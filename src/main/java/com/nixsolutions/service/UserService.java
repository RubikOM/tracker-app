package com.nixsolutions.service;

import org.springframework.stereotype.Service;

import com.nixsolutions.entity.User;

@Service
public class UserService {

    public User findByLogin(String login) {
        return new User();
    }
}
