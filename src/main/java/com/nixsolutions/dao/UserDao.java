package com.nixsolutions.dao;

import com.nixsolutions.entity.User;

public interface UserDao {

    User findByLogin(String login);
}
