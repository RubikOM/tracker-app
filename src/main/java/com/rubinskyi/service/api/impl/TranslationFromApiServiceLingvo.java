package com.rubinskyi.service.api.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;
import com.rubinskyi.service.api.TranslationFromApiService;

@Service
@Transactional
public class TranslationFromApiServiceLingvo implements TranslationFromApiService {
    private final ComprehensiveTranslationServiceLingvo comprehensiveDataService;
    private final UserService userService;

    @Autowired
    public TranslationFromApiServiceLingvo(ComprehensiveTranslationServiceLingvo comprehensiveDataService, UserService userService) {
        this.comprehensiveDataService = comprehensiveDataService;
        this.userService = userService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        String customizedWord = customizeString(wordInEnglish);
        User user = userService.findByLogin(principal.getName());

        DictionaryElement dictionaryElement = comprehensiveDataService.getDictionaryElementFromApi(customizedWord, user);
        return dictionaryElement;
    }

    private String customizeString(String wordToTranslate) {
        return wordToTranslate.trim().toLowerCase();
    }
}
