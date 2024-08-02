package org.capacity.service;

import org.capacity.connection.DatabaseAccessConnection;
import org.capacity.repository.RepositoryDAO;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.function.Function;

public class DatabaseManager {
    private final Jdbi jdbi;
    private static DatabaseManager instance;

    private DatabaseManager() {
        jdbi = new DatabaseAccessConnection().getJdbiConnection();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public <T> List<T> executeQuery(Function<RepositoryDAO, List<T>> queryResult) {
        return queryResult.apply(jdbi.onDemand(RepositoryDAO.class));
    }
}