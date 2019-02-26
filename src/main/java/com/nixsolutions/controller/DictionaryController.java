package com.nixsolutions.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.DictionaryService;

@Controller
public class DictionaryController {
    private static final String OUTPUT_FILE_NAME = "output.txt";

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/")
    public String getPage(Model model) {
        model.addAttribute("englishWord", new DictionaryElement());
//        model.addAttribute("words", vocabularyService.getDictionaryElementsWords());
        model.addAttribute("words", dictionaryService.getTodaysDictionaryElements());
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String addDictionaryElement(DictionaryElement dictionaryElement) {
        dictionaryService.addDictionaryElement(dictionaryElement);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeDictionaryElement(@PathVariable("deleteWord") String word) {
        dictionaryService.removeDictionaryElement(word);
    }

    // TODO change this method after
    // TODO try Baeldong's with text Plain
    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + OUTPUT_FILE_NAME);
        StringBuilder content = new StringBuilder();

        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements();
        for (DictionaryElement dictionaryElement : dictionaryElements) {
            content.append(dictionaryElement.getVocabularyElementAsString());
        }
        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }
}
