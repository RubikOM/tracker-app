package com.rubinskyi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.FormFillingService;

@RestController
public class FormFillingController {
    private final FormFillingService formFillingService;

    @Autowired
    public FormFillingController(FormFillingService formFillingService) {
        this.formFillingService = formFillingService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String wordInEnglish, Principal principal) {
        return formFillingService.getDictionaryElementFromApi(wordInEnglish, principal);
    }
}