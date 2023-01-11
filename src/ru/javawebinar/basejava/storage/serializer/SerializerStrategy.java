package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializerStrategy {
    void doWrite(Resume r, OutputStream outputStream) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
