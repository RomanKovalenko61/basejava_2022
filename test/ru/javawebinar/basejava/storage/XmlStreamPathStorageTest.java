package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}