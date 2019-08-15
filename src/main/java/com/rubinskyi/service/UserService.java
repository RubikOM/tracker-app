package com.rubinskyi.service;

import com.rubinskyi.entity.User;

public interface UserService {

    User findByLogin(String login);

    User save(User user);
}
