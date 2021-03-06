package com.rubinskyi.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolBean {
    private static final int THREAD_POOL_SIZE = 5;

    @Bean
    public ExecutorService multiWordExecutorService() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
}
