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
public class FormFillingService {
    private final PartialDataService partialDataService;
    private final ComprehensiveDataService comprehensiveDataService;
    private final UserService userService;

    public FormFillingService(@Autowired PartialDataService partialDataService, ComprehensiveDataService comprehensiveDataService,
                              UserService userService) {
        this.partialDataService = partialDataService;
        this.comprehensiveDataService = comprehensiveDataService;
        this.userService = userService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        String translation = partialDataService.getTranslationFromApi(wordInEnglish);
        Map additionalData = comprehensiveDataService.obtainDataFromApi(wordInEnglish, user);

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
