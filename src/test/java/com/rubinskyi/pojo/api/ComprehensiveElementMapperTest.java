package com.rubinskyi.pojo.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class, HibernateConfig.class})
public class ComprehensiveElementMapperTest {

    @Autowired
    private ComprehensiveElementMapper comprehensiveElementMapper;

    @Test
    public void comprehensiveElementToDictionaryElement_shouldReturnCorrectValue() {
        ComprehensiveElementLingvo elementToMap = new ComprehensiveElementLingvo();
        elementToMap.setDictionaryName("LingvoUniversal");
        elementToMap.setHeading("space");
        elementToMap.setTranscription("speɪs");
        elementToMap.setTranslations("пространство");
        elementToMap.setExamples("airspace; воздушное пространство");

        DictionaryElement expectedResult = new DictionaryElement.Builder("space", "пространство")
                .exampleTranslation("воздушное пространство")
                .example("airspace")
                .transcription("speɪs")
                .build();

        DictionaryElement actualResult = comprehensiveElementMapper.comprehensiveElementToDictionaryElement(elementToMap);

        assertEquals(expectedResult, actualResult);
    }
}
