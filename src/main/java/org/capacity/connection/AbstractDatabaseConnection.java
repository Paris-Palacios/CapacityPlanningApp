package org.capacity.connection;

import org.capacity.utilities.Constants;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;

public abstract class AbstractDatabaseConnection {
    protected Jdbi jdbi;

    public AbstractDatabaseConnection(String databaseName, String dbDriver) {
        String dbPath = System.getProperty(Constants.USER_DIR) + File.separator + databaseName;
        jdbi = Jdbi.create(dbDriver + dbPath);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public Jdbi getJdbiConnection() {
        return jdbi;
    }
}
