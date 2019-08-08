package com.rubinskyi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.config.bean.ObjectMapperBeans;
import com.rubinskyi.entity.Dictionary;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {"com.rubinskyi.service, com.rubinskyi.pojo, com.rubinskyi.dao," +
        " com.rubinskyi.config.bean"})
@ComponentScan(
        basePackageClasses = HibernateConfig.class,
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = HibernateConfig.class)})
public class SpringTestConfig {
    @Bean("userForTest")
    public User getTestUser() {
        Set<Interest> interests = new HashSet<>();
        User userForTest = new User(1L, "mike", "mockPassword");

        interests.add(new Interest(userForTest, new Dictionary("LingvoComputer (En-Ru)"), 1));
        interests.add(new Interest(userForTest, new Dictionary("LingvoUniversal (En-Ru)"), 2));
        interests.add(new Interest(userForTest, new Dictionary("Learning (En-Ru)"), 3));
        userForTest.setInterests(interests);

        return userForTest;
    }
}
