package com.rubinskyi.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.SpringConfiguration;
import com.rubinskyi.dao.UserDao;
import com.rubinskyi.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, SpringConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByName() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setLogin("user1");
        expectedUser.setPassword("user1Pass");

        assertEquals(expectedUser, userDao.findByLogin("user1"));
    }
}
