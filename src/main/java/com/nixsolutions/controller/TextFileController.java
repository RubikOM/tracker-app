package com.nixsolutions.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.service.DictionaryService;

@Controller
public class TextFileController {
    private static final String TODAY_FILE_NAME = LocalDate.now().toString() + ".txt";
    private static final String ALL_TIME_WORDS_FILE_NAME = "all your words.txt";

    private final DictionaryService dictionaryService;

    public TextFileController(@Autowired DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTodayTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + TODAY_FILE_NAME);
        StringBuilder content = new StringBuilder();

        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements();
        dictionaryElements.forEach(dictionaryElement -> content.append(dictionaryElement.getDictionaryElementAsString()));
        return content.toString();
    }

    @GetMapping(value = "/getAllTimeTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllTimeTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + ALL_TIME_WORDS_FILE_NAME);
        StringBuilder content = new StringBuilder();

        List<DictionaryElement> dictionaryElements = dictionaryService.getAllDictionaryElementsWords();
        dictionaryElements.forEach(dictionaryElement -> content.append(dictionaryElement.getDictionaryElementAsString()));
        return content.toString();
    }
}
