package com.njha.prototypes.db_connection_pooling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class NoPoolingService {

    private String url = "jdbc:postgresql://localhost:5432/test";
    private String user = "narendrajha";
    private String password = "narendrajha";

    public void executeSleepQuery() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT pg_sleep(?)")) {
            preparedStatement.setDouble(1, 0.01); // sleep 10ms
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
