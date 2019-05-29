package com.rubinskyi.service.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;

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

    // TODO now we using only ComprehensiveService, use partial to concatenate in the response
    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        String customizedWord = customizeString(wordInEnglish);
        User user = userService.findByLogin(principal.getName());

        String translation = partialDataService.obtainTranslationFromApi(customizedWord);
        DictionaryElement dictionaryElement = comprehensiveDataService.obtainDataFromApi(customizedWord, user);
        dictionaryElement.concatenateTranslations(translation);

        return dictionaryElement;
    }

    private String customizeString(String wordToTranslate) {
        return wordToTranslate.trim().toLowerCase();
    }
}
