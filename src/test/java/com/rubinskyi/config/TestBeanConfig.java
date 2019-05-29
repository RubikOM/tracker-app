package com.rubinskyi.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rubinskyi.entity.Dictionary;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;

@Configuration
@ComponentScan(basePackages = {"com.rubinskyi.service, com.rubinskyi.pojo.api"})
public class TestBeanConfig {

   /* @Autowired
    private UserService userService;
    private User user;*/

    // TODO component scan может залазить в папочку config ))))))))))))))))))))))))))
    // TODO дать отдельный Хибер конфиг для этих тестов, тащить реального юзера из БД!

    @Bean("userForTest")
    public User getTestUser() {
        Set<Interest> interests = new HashSet<>();
        User userForTest = new User(1L, "mike", "mockPassword");

        interests.add(new Interest(userForTest, new Dictionary("LingvoComputer"), 1));
        interests.add(new Interest(userForTest, new Dictionary("LingvoUniversal"), 2));
        interests.add(new Interest(userForTest, new Dictionary("Learning"), 3));
        userForTest.setInterests(interests);

        return userForTest;
    }

    /*@PostConstruct
    private void test() {
        user = userService.findByLogin("mike");
    }*/
}
