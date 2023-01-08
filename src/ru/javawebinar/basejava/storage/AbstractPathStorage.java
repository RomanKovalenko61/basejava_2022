package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected abstract void doWrite(Resume r, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory mist not be null");
        if (!Files.isDirectory(directory) || Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory");
        }
    }

    @Override
    protected boolean isExist(Path Path) {
        return false;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {

    }

    @Override
    protected void doSave(Resume r, Path Path) {

    }

    @Override
    protected void doDelete(Path Path) {

    }

    @Override
    protected Resume doGet(Path Path) {
        return null;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return null;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
