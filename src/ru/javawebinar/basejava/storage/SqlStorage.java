package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
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
            deleteContacts(r);
            deleteSection(r);
            insertContacts(conn, r);
            insertSection(conn, r);
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
            insertContacts(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("" +
                                 " SELECT * FROM resume r " +
                                 " LEFT JOIN contact c " + "" +
                                 " ON r.uuid = c.resume_uuid " +
                                 " WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContacts(rs, r);
                }
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("" +
                                 " SELECT * FROM resume r " +
                                 " LEFT JOIN section s " + "" +
                                 " ON r.uuid = s.resume_uuid " +
                                 " WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
            return r;
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM resume r " +
                                 " LEFT JOIN contact c " + "" +
                                 " ON r.uuid = c.resume_uuid " +
                                 " ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = getResume(resumeMap, rs);
                    addContacts(rs, r);
                }
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM resume r " +
                                 " LEFT JOIN section s " + "" +
                                 " ON r.uuid = s.resume_uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = getResume(resumeMap, rs);
                    addSection(rs, r);
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    private Resume getResume(Map<String, Resume> resumeMap, ResultSet rs) throws SQLException {
        String uuid = rs.getString("uuid");
        Resume r = resumeMap.get(uuid);
        if (r == null) {
            r = new Resume(uuid, rs.getString("full_name"));
            resumeMap.put(uuid, r);
        }
        return r;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("count") : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType type = e.getKey();
                ps.setString(2, type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((StringSection) e.getValue()).getString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        ps.setString(3, convert(((ListSection) e.getValue())));
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String convert(ListSection section) {
        StringBuilder sb = new StringBuilder();
        List<String> list = section.getList();
        for (String str : list) {
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }

    private List<String> convert(String str) {
        String[] strings = str.split("\n");
        return Arrays.asList(strings);
    }

    private void addContacts(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.setContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.setSection(type, new StringSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    r.setSection(type, new ListSection(convert(value)));
                    break;
            }
        }
    }

    private void deleteContacts(Resume r) {
        sqlHelper.<Void>execute("DELETE FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSection(Resume r) {
        sqlHelper.<Void>execute("DELETE FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }
}