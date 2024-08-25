package com.njha.prototypes.db_connection_pooling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class PoolingService {

    @Autowired
    private CustomConnectionPool customConnectionPool;

    public void executeSleepQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = customConnectionPool.get(); // get a connection obj from the custom pool
            preparedStatement = connection.prepareStatement("SELECT SLEEP(?)");
            preparedStatement.setDouble(1, 0.01); // sleep 10ms
            preparedStatement.execute();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement);
            customConnectionPool.add(connection); // add connection obj back to the pool
        }
    }

    private void closeResources(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
