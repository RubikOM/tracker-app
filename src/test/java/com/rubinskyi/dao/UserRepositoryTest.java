package com.rubinskyi.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByName() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setLogin("user1");
        expectedUser.setPassword("user1Pass");

        assertEquals(expectedUser, userRepository.findByLogin("user1"));
    }
}
