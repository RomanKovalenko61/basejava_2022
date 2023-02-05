package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.basejava.util.JsonParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlStreamPathStorageTest.class,
        JsonStreamPathStorageTest.class,
        JsonParserTest.class,
        DataStreamPathStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
