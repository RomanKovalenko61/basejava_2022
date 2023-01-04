package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> resumes = new ArrayList<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumes.size(); i++) {
            if (resumes.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        resumes.set((Integer) searchKey, r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        resumes.add(r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumes.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumes.get((Integer) searchKey);
    }

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(resumes);
    }

    @Override
    public int size() {
        return resumes.size();
    }
}
