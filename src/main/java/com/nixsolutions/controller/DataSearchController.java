package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.nixsolutions.entity.DictionaryElement;

// TODO rename it
@Controller
public class DataSearchController {

    @GetMapping("/fillPage/{word}")
    public RedirectView returnWordTranslationFromApi(@PathVariable String word, RedirectAttributes redir) {
        DictionaryElement result = new DictionaryElement.Builder(word, "mock").build();
        redir.addFlashAttribute("dictionaryElement", result);

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/dictionary");
        return redirectView;
    }
}
