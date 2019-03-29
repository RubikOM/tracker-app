package com.nixsolutions.service.api;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.entity.User;
import com.nixsolutions.service.UserService;

@Service
@Transactional
public class ApiService {
    private final TranslationService translationService;
    private final AdditionalDataService additionalDataService;
    private final UserService userService;

    public ApiService(@Autowired TranslationService translationService, AdditionalDataService additionalDataService,
                      UserService userService) {
        this.translationService = translationService;
        this.additionalDataService = additionalDataService;
        this.userService = userService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        String translation = translationService.getTranslationFromApi(wordInEnglish);
        Map additionalData = additionalDataService.getTranslationFromApi(wordInEnglish);
        User user = userService.findByLogin(principal.getName());

        // TODO prior translation(or concatenate) if it's made by needed Dictionary
        String transcription = (String) additionalData.getOrDefault("transcription", "");
        String example = (String) additionalData.getOrDefault("example", "");
        String exampleTranslation = (String) additionalData.getOrDefault("exampleTranslation", "");

        return new DictionaryElement.Builder(wordInEnglish, translation)
                .transcription("[" + transcription + "]")
                .example(example)
                .exampleTranslation(exampleTranslation)
                .build();
    }
}
