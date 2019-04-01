package com.rubinskyi.dao;

import com.rubinskyi.entity.User;

public interface UserDao {

    User findByLogin(String login);
}
