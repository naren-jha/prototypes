package com.njha.prototypes.db_connection_pooling;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class NoPoolingService {

    private String url = "jdbc:mysql://localhost:3306/test";
    private String user = "root";
    private String password = "root1234";

    public void executeSleepQuery() {
        // creates a new connection with the db each time this method is called
        // creating a new connection with the db each time involves
        // 1) A 3 way handshake to create connection
        // 2) 2 way request-response - query execution (the actual work)
        // 3) 2 way tear down

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // don't use dataSource.getConnection(); because that will get connection from Hikari pool
            connection = DriverManager.getConnection(url, user, password); // creates a new connection with the db each time

            preparedStatement = connection.prepareStatement("SELECT SLEEP(?)"); // pg_sleep - for PostgreSQL, SLEEP - for MySQL
            preparedStatement.setDouble(1, 0.01); // sleep 10ms
            preparedStatement.execute(); // query execution
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection); // tear down
        }
    }

    private void closeResources(PreparedStatement preparedStatement, Connection connection) {
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
