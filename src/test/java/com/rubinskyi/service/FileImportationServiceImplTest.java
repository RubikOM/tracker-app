package com.rubinskyi.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.rubinskyi.service.impl.TextFileImportationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FileImportationServiceImplTest {

    @Mock
    DictionaryElementService dictionaryElementService;
    @Mock
    User user;
    @InjectMocks
    TextFileImportationService textFileImportationService;

    @Test
    public void getTodayTxtFile() {
        DictionaryElement element0 = new DictionaryElement.Builder("space", "пауза").build();
        DictionaryElement element1 = new DictionaryElement.Builder("space", "расстояние").transcription("speɪs").build();
        DictionaryElement element2 = new DictionaryElement.Builder("space", "протяжённость").transcription("speɪs")
                .example("for the space of two kilometers").exampleTranslation("на расстоянии двух километров").build();
        List<DictionaryElement> dictionaryElements = Arrays.asList(element0, element1, element2);

        Mockito.when(dictionaryElementService.getTodaysDictionaryElements(user)).thenReturn(dictionaryElements);
        String todayTxtFile = textFileImportationService.getTodayTxtFile(user);
        String expectedResult = "space;пауза;\n" +
                "space;speɪs;расстояние;\n" +
                "space;speɪs;протяжённость;for the space of two kilometers;на расстоянии двух километров;\n";

        assertEquals(expectedResult, todayTxtFile);
    }

    @Test
    public void getAllTimeTxtFile() {
        DictionaryElement element0 = new DictionaryElement.Builder("space", "пауза").build();
        DictionaryElement element1 = new DictionaryElement.Builder("space", "расстояние").transcription("speɪs").build();
        DictionaryElement element2 = new DictionaryElement.Builder("space", "протяжённость").transcription("speɪs")
                .example("for the space of two kilometers").exampleTranslation("на расстоянии двух километров").build();
        List<DictionaryElement> dictionaryElements = Arrays.asList(element0, element1, element2);

        Mockito.when(dictionaryElementService.getAllDictionaryElements(user)).thenReturn(dictionaryElements);
        String todayTxtFile = textFileImportationService.getAllTimeTxtFile(user);
        String expectedResult = "space;пауза;\n" +
                "space;speɪs;расстояние;\n" +
                "space;speɪs;протяжённость;for the space of two kilometers;на расстоянии двух километров;\n";

        assertEquals(expectedResult, todayTxtFile);
    }
}