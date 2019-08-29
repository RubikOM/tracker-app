package com.rubinskyi.service.outerApi;

import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
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
public class ComprehensiveTranslationServiceLingvoTest {
    @Autowired
    private ComprehensiveTranslationService comprehensiveTranslationService;
    @Autowired
    private User defaultUser;

    @Test
    public void obtainDataFromApi_shouldReturnCorrectResponse() {
        String wordToTranslate = "space";

        DictionaryElement expectedResult = new DictionaryElement.Builder("space",
                "пространство, область, расстояние, интервал")
                .transcription("[speɪs]")
                .example("airspace")
                .exampleTranslation("воздушное пространство")
                .build();

        DictionaryElement actualResult = comprehensiveTranslationService.getDictionaryElementFromApi(wordToTranslate, defaultUser);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void obtainDataFromApi_shouldReturnEmptyResponse() {
        String wordToTranslate = "abcad";

        DictionaryElement expectedResult = new DictionaryElement();
        expectedResult.setWord(wordToTranslate);

        DictionaryElement actualResult = comprehensiveTranslationService.getDictionaryElementFromApi(wordToTranslate, defaultUser);

        assertEquals(expectedResult, actualResult);
    }
}
