package com.rubinskyi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.rubinskyi.service", "com.rubinskyi.bean", "com.rubinskyi.testBean", "com.rubinskyi.util.file"})
@Import({JpaConfiguration.class})
public class SpringTestConfig {
}
