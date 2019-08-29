package com.rubinskyi.service.outerApi.impl;

import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.testBean.TextFileReaderBean;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;
import com.rubinskyi.service.outerApi.SuggestedTranslationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringTestConfig.class})
public class SuggestedTranslationServiceLingvoTest {
    @Autowired
    private User defaultUser;
    @Autowired
    private SuggestedTranslationService suggestedTranslationService;
    @Autowired
    TextFileReaderBean textFileReaderBean;
    @MockBean
    private UserService userService;

    @Before
    public void prepareMockUser() {
        when(userService.findByLogin(Mockito.anyString())).thenReturn(defaultUser);
    }

    @Test
    public void getSuggestedElements() {
        String initialString = textFileReaderBean.getContentByFileName("ocrTextFiles/tesseractTestData.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(4, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_sentence() {
        String initialString = textFileReaderBean.getContentByFileName("ocrTextFiles/tesseractTestDataCropped.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_largeText() {
        String initialString = textFileReaderBean.getContentByFileName("ocrTextFiles/realBook.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }
}
