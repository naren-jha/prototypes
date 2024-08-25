package com.njha.prototypes.db_connection_pooling;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class ConnectionPoolPrototype {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ExecutorService executorService2;

    @Autowired
    private NoPoolingService noPoolingService;

    @Autowired
    private PoolingService poolingService;

    public void benchmarkNonPool() {
        Instant start = Instant.now();

        for (int i = 0; i < 30000; i++) { // fails at 35000
            executorService.submit(() -> noPoolingService.executeSleepQuery());
        }

        executorService.shutdown(); // don't accept anymore tasks in the pool
        while (!executorService.isTerminated()) {
            // Wait for all tasks to finish
        }

        Instant end = Instant.now();
        log.info("Execution without pooling completed in: " + Duration.between(start, end).toMillis() + " ms");
    }


    public void benchmarkPool() {
        Instant start = Instant.now();

        for (int i = 0; i < 1000; i++) {
            executorService2.submit(() -> poolingService.executeSleepQuery());
        }

        executorService2.shutdown(); // don't accept anymore tasks in the pool
        while (!executorService2.isTerminated()) {
            // Wait for all tasks to finish
        }

        Instant end = Instant.now();
        log.info("Execution with pooling completed in: " + Duration.between(start, end).toMillis() + " ms");
    }

    @PostConstruct
    public void test() {
        benchmarkNonPool();
        //benchmarkPool();
    }

}
