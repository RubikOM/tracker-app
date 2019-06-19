package com.rubinskyi.service.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class, HibernateConfig.class})
public class ComprehensiveTranslationServiceLingvoTest {

    @Autowired
    private ComprehensiveTranslationService comprehensiveTranslationService;
    @Autowired
    private User userForTest;

    @Test
    public void obtainDataFromApi_shouldReturnCorrectResponse() {
        String wordToTranslate = "space";

        DictionaryElement expectedResult = new DictionaryElement.Builder("space", "пространство; область")
                .transcription("speɪs")
                .example("airspace")
                .exampleTranslation("воздушное пространство")
                .build();

        DictionaryElement actualResult = comprehensiveTranslationService.obtainDataFromApi(wordToTranslate, userForTest);

        assertEquals(expectedResult, actualResult);
    }
}
