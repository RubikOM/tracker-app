package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.Pages;

// TODO rename it
@Controller
public class DataSearchController {
    @GetMapping("/fillPage/{word}")
    public String returnWordTranslationFromApi(@PathVariable String word, Model model) {
        model.addAttribute("dictionaryElement", new DictionaryElement.Builder(word, "mock").build());

        // TODO result is not displaying
        return Pages.DICTIONARY_PAGE.getPage();
    }
}
