package com.njha.prototypes.db_connection_pooling;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class CustomConnectionPool {

    private static final int POOL_SIZE = 10;

    private BlockingQueue<Connection> connectionPool;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initializeConnectionPool() throws SQLException {
        connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            connectionPool.add(dataSource.getConnection());
        }
    }

    public Connection getConnection() throws InterruptedException {
        return connectionPool.take();
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.offer(connection);
        }
    }

    @PreDestroy
    public void shutdownPool() throws SQLException {
        for (Connection connection : connectionPool) {
            connection.close();
        }
    }
}
