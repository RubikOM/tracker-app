package com.rubinskyi.service.api;

import java.security.Principal;
import java.util.Map;

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

    public FormFillingServiceLingvo(@Autowired PartialDataServiceLingvo partialDataService, ComprehensiveDataServiceLingvo comprehensiveDataService,
                                    UserService userService) {
        this.partialDataService = partialDataService;
        this.comprehensiveDataService = comprehensiveDataService;
        this.userService = userService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        String translation = partialDataService.obtainTranslationFromApi(wordInEnglish);
        Map additionalData = comprehensiveDataService.obtainDataFromApi(wordInEnglish, user);

        String transcription = (String) additionalData.getOrDefault("transcription", "");
        String example = (String) additionalData.getOrDefault("example", "");
        String exampleTranslation = (String) additionalData.getOrDefault("exampleTranslation", "");

        return new DictionaryElement.Builder(wordInEnglish, removeExtraSymbols(translation))
                .transcription("[" + transcription + "]")
                .example(removeExtraSymbols(example))
                .exampleTranslation(removeExtraSymbols(exampleTranslation))
                .build();
    }

    private String removeExtraSymbols(String stringToClean) {
        return stringToClean.replace(";", "");
    }
}
