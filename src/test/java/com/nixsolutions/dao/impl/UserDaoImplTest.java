package com.nixsolutions.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nixsolutions.config.HibernateConfig;
import com.nixsolutions.config.SpringConfiguration;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
// TODO @Transactional this is not working on test class
@ContextConfiguration(classes = {HibernateConfig.class, SpringConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DatabaseSetup("/dataSet/TestDataSet.xml")
    public void findByName() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("user1");
        expectedUser.setPassword("user1Pass");

        assertEquals(expectedUser, userDao.findByLogin("user1"));
    }
}
