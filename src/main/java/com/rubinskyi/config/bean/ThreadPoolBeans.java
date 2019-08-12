package com.rubinskyi.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:api.properties")
public class ThreadPoolBeans {
    @Value("${multiWordThreadPoolSize}")
    private int MULTI_WORD_THREAD_POOL_SIZE;
    @Value("${lingvoThreadPoolSize}")
    private int LINGVO_THREAD_POOL_SIZE;

    @Bean
    public ExecutorService multiWordExecutorService() {
        return Executors.newFixedThreadPool(MULTI_WORD_THREAD_POOL_SIZE);
    }

    @Bean
    public ExecutorService lingvoExecutorService() {
        return Executors.newFixedThreadPool(LINGVO_THREAD_POOL_SIZE);
    }

}
