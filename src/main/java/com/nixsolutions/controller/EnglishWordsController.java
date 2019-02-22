package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nixsolutions.entity.EnglishWord;
import com.nixsolutions.pojo.Pages;

@Controller
public class EnglishWordsController {
    @GetMapping("/")
    public String helloWorld() {
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String test(EnglishWord englishWord) {
        System.out.println(englishWord);
        return "redirect:/";
    }
}
