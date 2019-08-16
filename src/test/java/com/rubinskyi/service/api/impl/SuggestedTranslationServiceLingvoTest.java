package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.config.TextFileReader;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;
import com.rubinskyi.service.api.SuggestedTranslationService;
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
    private User testUser;
    @Autowired
    private SuggestedTranslationService suggestedTranslationService;
    @Autowired
    TextFileReader textFileReader;
    @MockBean
    private UserService userService;

    @Before
    public void prepareMockUser() {
        /*Set<Interest> interests = new HashSet<>();
        User userForTest = new User(1L, "mike", "mockPassword");
        interests.add(new Interest(userForTest, new Dictionary("LingvoComputer (En-Ru)"), 1));
        interests.add(new Interest(userForTest, new Dictionary("LingvoUniversal (En-Ru)"), 2));
        interests.add(new Interest(userForTest, new Dictionary("Learning (En-Ru)"), 3));
        userForTest.setInterests(interests);
        testUser = userForTest;*/

        when(userService.findByLogin(Mockito.anyString())).thenReturn(testUser);
    }

    @Test
    public void getSuggestedElements() {
        String initialString = textFileReader.getContentByFileName("ocrText/tesseractTestData.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(4, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_sentence() {
        String initialString = textFileReader.getContentByFileName("ocrText/tesseractTestDataCropped.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_largeText() {
        String initialString = textFileReader.getContentByFileName("ocrText/realBook.txt");
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }
}
