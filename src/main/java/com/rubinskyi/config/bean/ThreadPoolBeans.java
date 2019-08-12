package com.rubinskyi.config.bean;

import com.rubinskyi.config.properties.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolBeans {
    private ApiProperties apiProperties;

    @Autowired
    public ThreadPoolBeans(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    @Bean
    public ExecutorService multiWordExecutorService() {
        return Executors.newFixedThreadPool(apiProperties.getMultiWordThreadPoolSize());
    }

    @Bean
    public ExecutorService lingvoExecutorService() {
        return Executors.newFixedThreadPool(apiProperties.getLingvoThreadPoolSize());
    }
}
