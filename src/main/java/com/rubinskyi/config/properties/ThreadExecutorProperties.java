package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:threadExecutor.properties")
public class ThreadExecutorProperties {
    @Value("${thread.multiWordThreadPoolSize}")
    private int multiWordThreadPoolSize;
    @Value("${thread.lingvoThreadPoolSize}")
    private int lingvoThreadPoolSize;
}
