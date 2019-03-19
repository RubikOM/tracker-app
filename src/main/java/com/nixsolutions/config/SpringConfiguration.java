package com.nixsolutions.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.nixsolutions.service, com.nixsolutions.controller, com.nixsolutions.validation," +
        " com.nixsolutions.config.security, com.nixsolutions.pojo.api"})
public class SpringConfiguration {
}
