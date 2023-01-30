package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.sql.TransactionTemplate;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Config config = Config.getInstance();

    public SqlStorageTest() {
        super(new SqlHelper(new TransactionTemplate(config.getDbUrl(), config.getDbUser(), config.getDbPassword())));
    }
}