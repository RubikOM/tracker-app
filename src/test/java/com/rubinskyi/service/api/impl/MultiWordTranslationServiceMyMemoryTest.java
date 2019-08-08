package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.SpringTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class MultiWordTranslationServiceMyMemoryTest {

    @Autowired
    private MultiWordTranslationServiceMyMemory multiWordTranslation;

    @Test
    public void translateSentenceToRussian_shouldReturnCorrectResponse() {
        String uncroppedString = "PREREQUISITES In order to make the most of this, you will need to have a little bit of programming experience.";

        String expectedResult = "ПРЕДПОСЫЛКИ Для того, чтобы максимально использовать это, вам нужно иметь немного опыта программирования.";
        assertEquals(expectedResult, multiWordTranslation.translateSentenceToRussian(uncroppedString));
    }
}
