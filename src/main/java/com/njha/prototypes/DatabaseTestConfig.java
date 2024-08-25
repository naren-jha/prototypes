package com.njha.prototypes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
@Slf4j
public class DatabaseTestConfig {

//    @Bean
//    public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
//        return args -> {
//            try (Connection connection = dataSource.getConnection()) {
//                log.info("Connected to the database successfully!");
//            } catch (SQLException e) {
//                log.error("Failed to connect to the database.");
//                e.printStackTrace();
//            }
//        };
//    }
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Bean
//    public CommandLineRunner listDataSources() {
//        return args -> {
//            String[] dataSourceBeans = context.getBeanNamesForType(DataSource.class);
//            Arrays.stream(dataSourceBeans).forEach(beanName ->
//                    log.info("Found DataSource bean: {}", beanName));
//        };
//    }
}
