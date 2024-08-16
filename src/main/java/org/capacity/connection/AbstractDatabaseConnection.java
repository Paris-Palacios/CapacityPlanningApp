package org.capacity.connection;

import org.capacity.utilities.Constants;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public abstract class AbstractDatabaseConnection {
    protected Jdbi jdbi;
    protected String defaultDatabaseName;

    public AbstractDatabaseConnection(String defaultDatabaseName, String dbDriver) {
        this.defaultDatabaseName = defaultDatabaseName;
        String dbPath = getDatabasePath();
        jdbi = Jdbi.create(dbDriver + dbPath);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public Jdbi getJdbiConnection() {
        return jdbi;
    }

    public String getDatabasePath() {
        Properties properties = new Properties();
        String dbPath = null;
        try {
            FileInputStream input = new FileInputStream(System.getProperty(Constants.USER_DIR) + File.separator + Constants.CONFIG_FILE);
            properties.load(input);
            dbPath = properties.getProperty(Constants.DB_PATH_KEY);

        } catch (Exception e) {
            System.out.println("Error: " + "No config properties file found");
        }

        if (dbPath == null || dbPath.isEmpty()) {
            dbPath = System.getProperty(Constants.USER_DIR) + File.separator + defaultDatabaseName; // Default path
        }

        return dbPath;
    }
}
