package com.rubinskyi.controller;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.dto.DictionaryElementDto;
import com.rubinskyi.service.outerApi.TranslationFromApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TranslationFromApiController {
    private final TranslationFromApiService translationFromApiService;
    private static final String ERROR_MESSAGE = "Sorry, we didn't find anything, check your spelling";

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElementDto getDictionaryElementFromApi(@PathVariable String wordInEnglish, Principal principal) {
        String firstWord = getFirstWord(wordInEnglish);
        DictionaryElement dictionaryElementFromApi = translationFromApiService.getDictionaryElementFromApi(firstWord, principal);

        if (dictionaryElementFromApi.getTranslation() == null || dictionaryElementFromApi.getTranslation().isEmpty()) {
            return new DictionaryElementDto(wordInEnglish, ERROR_MESSAGE);
        } else return new DictionaryElementDto(dictionaryElementFromApi);
    }

    private String getFirstWord(String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }
}
