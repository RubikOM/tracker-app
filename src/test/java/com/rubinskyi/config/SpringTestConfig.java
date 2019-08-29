package com.rubinskyi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.rubinskyi.service", "com.rubinskyi.pojo", "com.rubinskyi.dao",
        "com.rubinskyi.config.bean", "com.rubinskyi.config.properties", "com.rubinskyi.util", "com.rubinskyi.testBean"})
@Import({JpaConfiguration.class})
public class SpringTestConfig {
}
