package com.rubinskyi.service.api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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
public class SimpleDictionaryElementConsolidatorServiceTest {

    @Autowired
    private DictionaryElementConsolidatorService dictionaryElementConsolidatorService;

    // TODO more tests here!!!!!!!!!
    @Test
    public void consolidateOneElementList_shouldReturnThisElement() {
        DictionaryElement elementToMap = new DictionaryElement();
        elementToMap.setWord("space");
        elementToMap.setTranscription("speɪs");
        elementToMap.setTranslation("пространство");
        elementToMap.setExample("airspace");
        elementToMap.setExampleTranslation("воздушное пространство");
        List<DictionaryElement> oneWordList = Arrays.asList(elementToMap);

        DictionaryElement expectedResult = new DictionaryElement.Builder("space", "пространство")
                .exampleTranslation("воздушное пространство")
                .example("airspace")
                .transcription("[speɪs]")
                .build();

        DictionaryElement actualResult = dictionaryElementConsolidatorService.consolidateDictionaryElements(oneWordList);

        assertEquals(expectedResult, actualResult);
    }
}
