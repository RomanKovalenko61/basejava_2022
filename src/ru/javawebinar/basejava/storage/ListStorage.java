package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> resumes = new ArrayList<>();

    @Override
    protected boolean isExist(Integer searchKey) {
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
    protected void doUpdate(Resume r, Integer searchKey) {
        resumes.set(searchKey, r);
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        resumes.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        resumes.remove(searchKey.intValue());
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return resumes.get(searchKey);
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
