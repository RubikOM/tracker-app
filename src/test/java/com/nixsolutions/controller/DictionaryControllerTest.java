/*
package com.nixsolutions.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.DictionaryService;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryControllerTest {
    @InjectMocks
    DictionaryController dictionaryController;

    @Mock
    DictionaryService dictionaryService;

    private MockMvc mockMvc;

    private List<DictionaryElement> dictionaryElements = Arrays.asList(
            new DictionaryElement.Builder("not empty list", "абра кадабра").build(),
            new DictionaryElement.Builder("another word", "абра кадабра два").build());

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dictionaryController).alwaysDo(print()).build();
    }

    @Test
    public void getPage() throws Exception {
//        when(dictionaryService.getTodaysDictionaryElements()).thenReturn(dictionaryElements);

        mockMvc
                .perform(get("/dictionary"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dictionaryElement"))
                .andExpect(view().name(Pages.ENGLISH_WORD_PAGE.getPage()));

//        verify(dictionaryService, times(1)).getTodaysDictionaryElements();
    }
}
*/
