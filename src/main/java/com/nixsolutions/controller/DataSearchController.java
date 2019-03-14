package com.nixsolutions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.entity.DictionaryElement;

// TODO rename it
@RestController
public class DataSearchController {

    /*@GetMapping("/fillPage/{word}")
    public RedirectView returnWordTranslationFromApi(@PathVariable String word, RedirectAttributes redir) {
        DictionaryElement result = new DictionaryElement.Builder(word, "mock").build();
        redir.addFlashAttribute("dictionaryElement", result);

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/dictionary");
        return redirectView;
    }*/

    @GetMapping("/fillPage/{word}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String word) {
        DictionaryElement result = new DictionaryElement.Builder(word, "mock").build();

        return result;
    }
}
