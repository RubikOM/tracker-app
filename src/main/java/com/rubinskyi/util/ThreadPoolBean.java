package com.rubinskyi.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolBean {
    private static final int THREAD_POOL_SIZE = 5;
    @Bean
    public ExecutorService multiWordExecutorService() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Bean
    public ExecutorService lingvoExecutorService() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
}
