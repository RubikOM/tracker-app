package com.rubinskyi.entity;

import com.rubinskyi.config.SpringTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class UserTest {

    @Autowired
    private User userForTest;

    @Test
    public void interestedInDictionary_shouldWorkCorrectly() {
        String dictionaryName = "LingvoComputer (En-Ru)";
        boolean interestedInDictionary = userForTest.interestedInDictionary(dictionaryName);

        assertTrue(interestedInDictionary);
    }
}
