package com.rubinskyi.entity;

import com.rubinskyi.config.SpringTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
public class UserTest {

    @Autowired
    private User defaultUser;

    @Test
    public void interestedInDictionary_shouldWorkCorrectly() {
        String dictionaryName = "LingvoComputer (En-Ru)";
        boolean interestedInDictionary = defaultUser.interestedInDictionary(dictionaryName);

        assertTrue(interestedInDictionary);
    }
}
