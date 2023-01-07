package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String FULL_NAME_1 = "Alex Smith";
    private static final String FULL_NAME_2 = "Boris Blade";
    private static final String FULL_NAME_3 = "Roman Smith";
    private static final String FULL_NAME_4 = "fullname";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.getResume(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.getResume(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.getResume(UUID_3, FULL_NAME_3);
        RESUME_4 = ResumeTestData.getResume(UUID_4, FULL_NAME_4);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        storage.save(RESUME_4);
        RESUME_4.setFullName("other fullname");
        storage.update(RESUME_4);
        assertEquals(storage.get(RESUME_4.getUuid()), RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(ResumeTestData.getResume("uuid", "dummy"));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
        assertEquals(RESUME_2, storage.get(RESUME_2.getUuid()));
        assertEquals(RESUME_3, storage.get(RESUME_3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_2.getUuid());
        assertSize(2);
        storage.get(RESUME_2.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        assertEquals(3, storage.size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}