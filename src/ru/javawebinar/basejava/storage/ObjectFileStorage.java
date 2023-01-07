package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectFileStorage extends AbstractFileStorage {

    public ObjectFileStorage() {
        super(new File("D:\\Projects\\basejava_2022\\storage"));
    }

    @Override
    protected void doWrite(Resume r, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(r);
        oos.flush();
        oos.close();
    }

    @Override
    protected Resume doRead(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Resume resume = (Resume) ois.readObject();
        ois.close();
        return resume;
    }
}
