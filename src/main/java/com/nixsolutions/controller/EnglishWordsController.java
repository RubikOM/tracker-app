package com.nixsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nixsolutions.entity.EnglishWord;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.EnglishWordService;

@Controller
public class EnglishWordsController {
    private final EnglishWordService englishWordService;

    @Autowired
    public EnglishWordsController(EnglishWordService englishWordService) {
        this.englishWordService = englishWordService;
    }

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("englishWord", new EnglishWord());
//        model.addAttribute("words", englishWordService.getAllWords());
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }

    // TODO rename everything
    /*@PostMapping("/createWord")
    public String createWord(EnglishWord englishWord) {
        englishWordService.createWord(englishWord);
        return "redirect:/";
    }*/
}
