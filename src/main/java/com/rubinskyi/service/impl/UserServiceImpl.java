package com.rubinskyi.service.impl;

import com.rubinskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubinskyi.dao.UserDao;
import com.rubinskyi.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
