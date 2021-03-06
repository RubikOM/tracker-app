package com.rubinskyi.bean;

import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.lingvo.ComprehensiveElementLingvo;
import com.rubinskyi.bean.mapper.ComprehensiveElementMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
public class ComprehensiveElementMapperSimpleTest {
    
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

    @Test
    public void comprehensiveElementToDictionaryElement_slashedExample() {
        ComprehensiveElementLingvo comprehensiveElementLingvo = new ComprehensiveElementLingvo();
        comprehensiveElementLingvo.setHeading("test");
        comprehensiveElementLingvo.setTranslations("испытание, испытания, проверка, контроль, испытывать");
        comprehensiveElementLingvo.setTranscription("test");
        comprehensiveElementLingvo.setExamples("difficult / demanding test — трудное испытание \n");

        DictionaryElement expectedResult = new DictionaryElement.Builder("test", "испытание, испытания, проверка, контроль, испытывать")
                .transcription("test")
                .example("difficult | demanding test")
                .exampleTranslation("трудное испытание")
                .build();

        assertEquals(expectedResult, comprehensiveElementMapper.comprehensiveElementToDictionaryElement(comprehensiveElementLingvo));
    }
}