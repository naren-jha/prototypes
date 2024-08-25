package com.njha.prototypes.db_connection_pooling;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class CustomConnectionPool {

    private static final int POOL_SIZE = 10;

    private BlockingQueue<Connection> connectionPool;

    private String url = "jdbc:mysql://localhost:3306/test";
    private String user = "root";
    private String password = "root1234";

    @PostConstruct
    public void initializeConnectionPool() throws SQLException {
        connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = DriverManager.getConnection(url, user, password);
            connectionPool.add(connection);
        }
    }

    public Connection get() throws InterruptedException {
        return connectionPool.take();
    }

    public void add(Connection connection) {
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
