package com.nixsolutions.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.DictionaryService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    private static final String TODAY_FILE_NAME = LocalDate.now().toString() + ".txt";
    private static final String ALL_TIME_WORDS_FILE_NAME = "all your words.txt";

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("")
    public String getPage(Model model) {
        model.addAttribute("dictionaryElement", new DictionaryElement());
        return getWordsTable(model);
    }

    @PostMapping("/createWord")
    public String addDictionaryElement(@Valid DictionaryElement dictionaryElement, BindingResult bindingResult,
                                       Model model) {
        // TODO make uniq in Database by annotation
        if (bindingResult.hasErrors()) {
            return getWordsTable(model);
        }
        if (dictionaryService.findByWord(dictionaryElement.getWord()) != null) {
            bindingResult.rejectValue("word", "word.existInDb");
            return getWordsTable(model);
        } else {
            dictionaryService.addDictionaryElement(dictionaryElement);
            return "redirect:/dictionary";
        }
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeDictionaryElement(@PathVariable("deleteWord") String word) {
        dictionaryService.removeDictionaryElement(word);
    }

    @PatchMapping("/edit/{editWord}")
    @ResponseBody
    public void editDictionaryElement(@PathVariable("editWord") DictionaryElement dictionaryElement) {
        /* NOP */
    }

    // TODO change this method after
    // TODO try Baeldong's with text Plain produces type
    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + TODAY_FILE_NAME);
        StringBuilder content = new StringBuilder();

        // TODO try to do it with Java8 - have to look better anyway
        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements();
        for (DictionaryElement dictionaryElement : dictionaryElements) {
            content.append(dictionaryElement.getVocabularyElementAsString());
        }
        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }

    @GetMapping(value = "/getAllTimeTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllTimeTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + ALL_TIME_WORDS_FILE_NAME);
        StringBuilder content = new StringBuilder();

        // TODO code duplications with getTxtFile
        List<DictionaryElement> dictionaryElements = dictionaryService.getAllDictionaryElementsWords();
        for (DictionaryElement dictionaryElement : dictionaryElements) {
            content.append(dictionaryElement.getVocabularyElementAsString());
        }
        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }

    private String getWordsTable(Model model) {
        List<DictionaryElement> elements = dictionaryService.getTodaysDictionaryElements();
        if (!elements.isEmpty()) {
            model.addAttribute("todaysAddedElements", elements);
        } else {
            model.addAttribute("lastAddedElements", dictionaryService.getLastDictionaryElementsWords());
        }
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }
}
