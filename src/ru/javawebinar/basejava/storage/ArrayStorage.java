package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndexIfResumeExists(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume with uuid = " + r.getUuid() + " is not found");
        }
    }

    public void save(Resume r) {
        if (size >= 10_000) {
            System.out.println("storage is overflow. size = 10_000");
        } else {
            int index = getIndexIfResumeExists(r.getUuid());
            if (index != -1) {
                System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        int index = getIndexIfResumeExists(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Resume with uuid = " + uuid + " is not found");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndexIfResumeExists(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with uuid = " + uuid + " is not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndexIfResumeExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
