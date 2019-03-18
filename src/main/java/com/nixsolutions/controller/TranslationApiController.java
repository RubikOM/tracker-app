package com.nixsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.service.TranslationApiService;

@RestController
public class TranslationApiController {
    private final TranslationApiService translationApiService;

    @Autowired
    public TranslationApiController(TranslationApiService translationApiService) {
        this.translationApiService = translationApiService;
    }

    @GetMapping("/fillPage/{word}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String word) {
        return translationApiService.getDictionaryElementFromApi(word);
    }
}
