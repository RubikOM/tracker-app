package com.nixsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.service.ApiService;

@RestController
public class TranslationApiController {
    private final ApiService apiService;

    @Autowired
    public TranslationApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String wordInEnglish) {
        return apiService.getDictionaryElementFromApi(wordInEnglish);
    }
}
