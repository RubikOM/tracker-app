package com.nixsolutions.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.form.DictionaryElementForm;
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

    @GetMapping
    /*
     * @return Pages.ENGLISH_WORD_PAGE.getPage() from private method getWordsTable() and on of lists with
     * dictionaryElements : created today one's if present and all time created if not.
     */
    public String getPage(Model model) {
        model.addAttribute("dictionaryElement", new DictionaryElementForm());
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

    @GetMapping("/edit/{editWord}")
    public String getEditDictionaryElementPage(@PathVariable("editWord") String word, Model model) {
        model.addAttribute("dictionaryElement", dictionaryService.findByWord(word));
        return Pages.EDIT_WORD_PAGE.getPage();
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
