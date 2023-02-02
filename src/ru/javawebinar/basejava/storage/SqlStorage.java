package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE contact SET value = ? WHERE type = ? AND resume_uuid=? ")) {
                writeContacts(r, ps);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO contact (value, type, resume_uuid) VALUES (?,?,?)")) {
                writeContacts(r, ps);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        " SELECT * FROM resume r " +
                        " LEFT JOIN contact c " + "" +
                        " ON r.uuid = c.resume_uuid " +
                        " WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return read(rs).get(uuid);
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r " +
                        " LEFT JOIN contact c " + "" +
                        " ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes;
                    if (rs.next()) {
                        resumes = new ArrayList<>(read(rs).values());
                    } else {
                        resumes = Collections.emptyList();
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("count") : 0;
        });
    }

    private Map<String, Resume> read(ResultSet rs) throws SQLException {
        Map<String, Resume> resumeMap = new LinkedHashMap<>();
        do {
            String uuid = rs.getString("uuid");
            Resume temp = resumeMap.get(uuid);
            if (temp == null) {
                temp = new Resume(uuid, rs.getString("full_name"));
            }
            String t = rs.getString("type");
            if (t != null) {
                ContactType type = ContactType.valueOf(t);
                String value = rs.getString("value");
                temp.setContact(type, value);
            }
            resumeMap.put(uuid, temp);
        } while (rs.next());
        return resumeMap;
    }

    private void writeContacts(Resume r, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
            ps.setString(1, entry.getValue());
            ps.setString(2, entry.getKey().name());
            ps.setString(3, r.getUuid());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}