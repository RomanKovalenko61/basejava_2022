package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectFileStorage extends AbstractFileStorage {

    public ObjectFileStorage() {
        super(new File("D:\\Projects\\basejava_2022\\storage"));
    }

    @Override
    protected void doWrite(Resume r, OutputStream outputStream) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(r);
        oos.flush();
        oos.close();
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Resume resume = (Resume) ois.readObject();
        ois.close();
        return resume;
    }
}
