package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> resumes = new ArrayList<>();

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    public void update(Resume r) {
        resumes.set(checkRange(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        resumes.add(r);
    }

    @Override
    public Resume get(String uuid) {
        return resumes.get(checkRange(uuid));
    }

    @Override
    public void delete(String uuid) {
        resumes.remove(checkRange(uuid));
    }

    @Override
    public Resume[] getAll() {
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumes.size();
    }

    @Override
    protected int getIndex(String uuid) {
        return resumes.indexOf(new Resume(uuid));
    }

    private int checkRange(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
