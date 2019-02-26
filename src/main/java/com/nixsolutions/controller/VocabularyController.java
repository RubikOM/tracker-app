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

import com.nixsolutions.entity.VocabularyElement;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.VocabularyService;

@Controller
public class VocabularyController {
    private static final String OUTPUT_FILE_NAME = "output.txt";

    private final VocabularyService vocabularyService;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/")
    public String getPage(Model model) {
        model.addAttribute("englishWord", new VocabularyElement());
//        model.addAttribute("words", vocabularyService.getVocabularyElementsWords());
        model.addAttribute("words", vocabularyService.getTodaysVocabularyElements());
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String addVocabularyElement(VocabularyElement vocabularyElement) {
        vocabularyService.addVocabularyElement(vocabularyElement);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeVocabularyElement(@PathVariable("deleteWord") String word) {
        vocabularyService.removeVocabularyElement(word);
    }

    // TODO change this method after
    // TODO try Baeldong's with text Plain
    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + OUTPUT_FILE_NAME);
        StringBuilder content = new StringBuilder();

        List<VocabularyElement> vocabularyElements = vocabularyService.getTodaysVocabularyElements();
        for (VocabularyElement vocabularyElement : vocabularyElements) {
            content.append(vocabularyElement.getVocabularyElementAsString());
        }
        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }
}
