package com.rubinskyi.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rubinskyi.dao.UserDao;
import com.rubinskyi.entity.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private final static String SELECT_USER_BY_LOGIN = "from User where login = :param";

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByLogin(String login) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_USER_BY_LOGIN);
        query.setParameter("param", login);
        try {
            return (User) query.uniqueResult();
        } catch (HibernateException e) {
            LOGGER.error(login + "not uniq user in system");
            List<User> result = query.list();
            return result.get(result.size() - 1);
        }
    }
}
