package com.rubinskyi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.rubinskyi.service", "com.rubinskyi.controller", "com.rubinskyi.config.security",
        "com.rubinskyi.pojo", "com.rubinskyi.dao", "com.rubinskyi.config.properties"})
public class SpringConfiguration {
}
