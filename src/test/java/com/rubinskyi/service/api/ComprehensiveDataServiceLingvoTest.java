package com.rubinskyi.service.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.TestBeanConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class, HibernateConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ComprehensiveDataServiceLingvoTest {

    @Autowired
    private ComprehensiveTranslationService comprehensiveTranslationService;
    @Autowired
    private User userForTest;

    @Test
    public void obtainDataFromApi_shouldReturnCorrectResponse() {
        DictionaryElement expectedResult = new DictionaryElement.Builder("space", "пространство; область")
                .transcription("speɪs")
                .example("airspace")
                .exampleTranslation("воздушное пространство")
                .build();

        // TODO for some reason "  Creator  "  didn't work, check it later
        String wordToTranslate = "space";
        DictionaryElement actualResult = comprehensiveTranslationService.obtainDataFromApi(wordToTranslate, userForTest);
        System.out.println(actualResult.toString());

        assertEquals(expectedResult, actualResult);
    }
}
