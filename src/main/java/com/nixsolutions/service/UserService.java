package com.nixsolutions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;

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
