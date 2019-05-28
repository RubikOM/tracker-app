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
public class FormFillingServiceLingvo implements FormFillingService {
    private final PartialDataServiceLingvo partialDataService;
    private final ComprehensiveDataServiceLingvo comprehensiveDataService;
    private final UserService userService;

    public FormFillingServiceLingvo(@Autowired PartialDataServiceLingvo partialDataService,
                                    ComprehensiveDataServiceLingvo comprehensiveDataService, UserService userService) {
        this.partialDataService = partialDataService;
        this.comprehensiveDataService = comprehensiveDataService;
        this.userService = userService;
    }

    // TODO now we using only ComprehensiveService, use partial to concatenate in the response
    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        String customizedWord = customizeString(wordInEnglish);
        User user = userService.findByLogin(principal.getName());
       /* String translation = partialDataService.obtainTranslationFromApi(customizedWord);
        Map additionalData = comprehensiveDataService.obtainDataFromApi(customizedWord, user);

        String transcription = (String) additionalData.getOrDefault("transcription", "");
        String example = (String) additionalData.getOrDefault("example", "");
        String exampleTranslation = (String) additionalData.getOrDefault("exampleTranslation", "");*/

        DictionaryElement dictionaryElement = comprehensiveDataService.obtainDataFromApi(customizedWord, user);

        return dictionaryElement;
    }

    // TODO re-write this method
    private String removeExtraSymbols(String stringToClean) {
        return stringToClean.replace(";", "");
    }

    private String customizeString(String wordToTranslate) {
        return wordToTranslate.trim().toLowerCase();
    }
}
