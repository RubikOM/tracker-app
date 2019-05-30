package com.rubinskyi.controller.translator;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.TranslationFromApiService;

@RestController
public class TranslationFromApiController {
    private final TranslationFromApiService translationFromApiService;

    @Autowired
    public TranslationFromApiController(TranslationFromApiService translationFromApiService) {
        this.translationFromApiService = translationFromApiService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String wordInEnglish, Principal principal) {
        DictionaryElement dictionaryElementFromApi = translationFromApiService.getDictionaryElementFromApi(wordInEnglish, principal);

        if (dictionaryElementFromApi == null || dictionaryElementFromApi.getTranslation().isEmpty()) {
            return new DictionaryElement.Builder(wordInEnglish, "!!! We can't find this word in DB, please try something else").build();
        } else return dictionaryElementFromApi;
    }
}
