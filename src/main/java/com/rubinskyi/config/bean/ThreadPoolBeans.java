package com.rubinskyi.config.bean;

import com.rubinskyi.config.properties.ApiProperties;
import com.rubinskyi.config.properties.ThreadExecutorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ThreadPoolBeans {
    private final ThreadExecutorProperties threadExecutorProperties;

    @Bean
    public ExecutorService multiWordExecutorService() {
        return Executors.newFixedThreadPool(threadExecutorProperties.getMultiWordThreadPoolSize());
    }

    @Bean
    public ExecutorService lingvoExecutorService() {
        return Executors.newFixedThreadPool(threadExecutorProperties.getLingvoThreadPoolSize());
    }
}
