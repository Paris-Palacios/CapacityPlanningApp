package org.capacity.connection;

import org.capacity.utilities.Constants;

public class DatabaseAccessConnection extends AbstractDatabaseConnection {

    public DatabaseAccessConnection() {
        super(Constants.ACCESS_DATABASE_NAME, Constants.DB_ACCESS_DRIVER);
    }
}

