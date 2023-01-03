package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.getOrDefault(uuid, null);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.put(((Resume) searchKey).getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(((Resume) searchKey).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>(map.values());
        resumes.sort(Comparator.comparing(Resume::getUuid));
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
