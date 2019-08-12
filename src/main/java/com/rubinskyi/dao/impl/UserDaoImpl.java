package com.rubinskyi.dao.impl;

import com.rubinskyi.dao.UserDao;
import com.rubinskyi.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;
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
            Object result = query.uniqueResult();
            return (User) result;
        } catch (HibernateException e) {
            log.error(login + "not uniq user in system");
            List<User> result = query.list();
            return result.get(result.size() - 1);
        }
    }
}
