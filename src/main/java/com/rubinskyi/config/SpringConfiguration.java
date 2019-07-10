package com.rubinskyi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.rubinskyi.service, com.rubinskyi.controller" +
        " com.rubinskyi.config.security, com.rubinskyi.pojo"})
public class SpringConfiguration {
}
