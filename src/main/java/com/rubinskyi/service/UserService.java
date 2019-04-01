package com.rubinskyi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubinskyi.dao.UserDao;
import com.rubinskyi.entity.User;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
