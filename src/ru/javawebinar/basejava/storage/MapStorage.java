package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        if (getIndex(uuid) > 0) {
            map.put(r.getUuid(), r);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if (getIndex(uuid) < 0) {
            map.put(r.getUuid(), r);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (getIndex(uuid) > 0) {
            return map.get(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (getIndex(uuid) > 0) {
            map.remove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>(map.values());
        Collections.sort(resumes);
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected int getIndex(String uuid) {
        if (map.containsKey(uuid)) {
            return 1;
        } else {
            return -1;
        }
    }
}
