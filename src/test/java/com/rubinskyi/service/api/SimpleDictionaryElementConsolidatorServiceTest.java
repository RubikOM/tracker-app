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

    @Test
    public void consolidateList_shouldReturnCorrectResponse() {
        DictionaryElement elementToMap1 = new DictionaryElement.Builder("space", "пространство; область").build();
        DictionaryElement elementToMap2 = new DictionaryElement.Builder("space", "расстояние; интервал; промежуток; пропуск; пробел").build();
        DictionaryElement elementToMap3 = new DictionaryElement.Builder("space", "располагать [размещать] с интервалами или вразрядку; оставлять пробел").build();
        DictionaryElement elementToMap4 = new DictionaryElement.Builder("space", "пауза").build();
        DictionaryElement elementToMap5 = new DictionaryElement.Builder("space", "расстояние").transcription("speɪs").build();
        DictionaryElement elementToMap6 = new DictionaryElement.Builder("space", "протяжённость").transcription("speɪs")
                .example("for the space of two kilometers").exampleTranslation("на расстоянии двух километров").build();
        DictionaryElement elementToMap7 = new DictionaryElement.Builder("space", "интервал времени; промежуток")
                .transcription("speɪs").example("in the space of a fortnight").exampleTranslation("срок; фиксированный промежуток времени").build();
        DictionaryElement elementToMap8 = new DictionaryElement.Builder("space", "срок; фиксированный промежуток времени")
                .transcription("speɪs").example("This problem should be solved within a short space of time.").exampleTranslation("Данную проблему необходимо разрешить за короткий срок.").build();

        List<DictionaryElement> wordsList = Arrays.asList(elementToMap1, elementToMap2, elementToMap3, elementToMap4,
                elementToMap5, elementToMap6, elementToMap7, elementToMap8);

        DictionaryElement expectedResult = new DictionaryElement.Builder("space",
                "пространство, область, расстояние, интервал")
                .exampleTranslation("на расстоянии двух километров")
                .example("for the space of two kilometers")
                .transcription("[speɪs]")
                .build();

        DictionaryElement actualResult = dictionaryElementConsolidatorService.consolidateDictionaryElements(wordsList);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void consolidateDictionaryElements_shouldReturnEmptyExample_whenTheyAreEmpty() {
        DictionaryElement elementToMap1 = new DictionaryElement.Builder("space", "пространство; область").build();
        DictionaryElement elementToMap2 = new DictionaryElement.Builder("space", "расстояние; интервал; промежуток; пропуск; пробел").build();
        DictionaryElement elementToMap3 = new DictionaryElement.Builder("space", "располагать [размещать] с интервалами или вразрядку; оставлять пробел").build();
        DictionaryElement elementToMap4 = new DictionaryElement.Builder("space", "пауза").build();
        DictionaryElement elementToMap5 = new DictionaryElement.Builder("space", "расстояние").transcription("speɪs").build();
        DictionaryElement elementToMap6 = new DictionaryElement.Builder("space", "протяжённость").transcription("speɪs").build();
        DictionaryElement elementToMap7 = new DictionaryElement.Builder("space", "интервал времени; промежуток")
                .transcription("speɪs").build();
        DictionaryElement elementToMap8 = new DictionaryElement.Builder("space", "срок; фиксированный промежуток времени")
                .transcription("speɪs").build();

        List<DictionaryElement> wordsList = Arrays.asList(elementToMap1, elementToMap2, elementToMap3, elementToMap4,
                elementToMap5, elementToMap6, elementToMap7, elementToMap8);

        DictionaryElement expectedResult = new DictionaryElement.Builder("space",
                "пространство, область, расстояние, интервал")
                .exampleTranslation("")
                .example("")
                .transcription("[speɪs]")
                .build();

        DictionaryElement actualResult = dictionaryElementConsolidatorService.consolidateDictionaryElements(wordsList);
        assertEquals(expectedResult, actualResult);
    }
}
