package com.njha.prototypes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {

    @Value("${executor.core.pool.size}")
    private int poolSize;

    @Value("${maximum.pool.size}")
    private int maximumPoolSize;

    @Value("${keep.alive.time.sec}")
    private int keepAliveTimeSec;

    @Bean("executorService")
    public ExecutorService getExecutorService(){
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, maximumPoolSize, keepAliveTimeSec,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        return executorService;
    }

    @Bean("executorService2")
    public ExecutorService getExecutorService2(){
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, maximumPoolSize, keepAliveTimeSec,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        return executorService;
    }
}