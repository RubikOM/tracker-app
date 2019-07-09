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
    private final PartialTranslationServiceLingvo partialDataService;
    private final ComprehensiveTranslationServiceLingvo comprehensiveDataService;
    private final UserService userService;

    public TranslationFromApiServiceLingvo(@Autowired PartialTranslationServiceLingvo partialDataService,
                                           ComprehensiveTranslationServiceLingvo comprehensiveDataService, UserService userService) {
        this.partialDataService = partialDataService;
        this.comprehensiveDataService = comprehensiveDataService;
        this.userService = userService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        String customizedWord = customizeString(wordInEnglish);
        User user = userService.findByLogin(principal.getName());

        String translation = partialDataService.obtainTranslationFromApi(customizedWord);
        DictionaryElement dictionaryElement = comprehensiveDataService.getDictionaryElementFromApi(customizedWord, user);
        dictionaryElement.setTranslation(dictionaryElement.getTranslation().concat(translation));

        return dictionaryElement;
    }

    private String customizeString(String wordToTranslate) {
        return wordToTranslate.trim().toLowerCase();
    }
}