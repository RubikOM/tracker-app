package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.TextFileReader;
import com.rubinskyi.config.SpringTestConfig;
import org.junit.Ignore;
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
public class MultiWordTranslationServiceMyMemoryTest {

    @Autowired
    private MultiWordTranslationServiceMyMemory multiWordTranslation;
    @Autowired
    TextFileReader textFileReader;

    @Test
    public void translateSentenceToRussian_shouldReturnCorrectResponse() {
        String initialString = textFileReader.getContentByFileName("ocrTextFiles/tesseractTestDataHeader.txt");

        String expectedResult = textFileReader.getContentByFileName("ocrTextFiles/tesseractTestDataHeaderTranslated.txt");
        assertEquals(expectedResult, multiWordTranslation.translateSentenceToRussian(initialString));
    }

    // API has amount of words a day to translate that's why @Ignore takes a place here
    @Test
    @Ignore
    public void translateSentenceToRussian_TranslatesTextBiggerThan500Characters() {
        String initialString = textFileReader.getContentByFileName("ocrTextFiles/tesseractTestData.txt");

        String expectedResult = textFileReader.getContentByFileName("ocrTextFiles/tesseractTestDataTranslated.txt");
        assertEquals(expectedResult, multiWordTranslation.translateTextToRussian(initialString));
    }
}
