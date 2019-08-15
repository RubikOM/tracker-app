package com.rubinskyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@ComponentScan({"com.rubinskyi.service", "com.rubinskyi.controller", "com.rubinskyi.config.security",
        "com.rubinskyi.pojo", "com.rubinskyi.dao", "com.rubinskyi.config.properties"})*/
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}