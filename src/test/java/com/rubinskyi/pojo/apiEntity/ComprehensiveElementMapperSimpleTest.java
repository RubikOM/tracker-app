package com.rubinskyi.pojo.apiEntity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.mapper.ComprehensiveElementMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class, HibernateConfig.class})
public class ComprehensiveElementMapperSimpleTest {

    // TODO test for empty and null Comprehensive elements
    @Autowired
    private ComprehensiveElementMapper comprehensiveElementMapper;

    @Test
    public void comprehensiveElementToDictionaryElement() {
        ComprehensiveElementLingvo comprehensiveElementLingvo = new ComprehensiveElementLingvo();
        comprehensiveElementLingvo.setHeading("space");
        comprehensiveElementLingvo.setTranslations("пространство; область");
        comprehensiveElementLingvo.setTranscription("speɪs");
        comprehensiveElementLingvo.setExamples("airspace/воздушное пространство");

        DictionaryElement expectedResult = new DictionaryElement.Builder("space", "пространство; область")
                .transcription("speɪs")
                .example("airspace")
                .exampleTranslation("воздушное пространство")
                .build();

        assertEquals(expectedResult, comprehensiveElementMapper.comprehensiveElementToDictionaryElement(comprehensiveElementLingvo));
    }
}