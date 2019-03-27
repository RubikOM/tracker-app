package com.nixsolutions.service.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.entity.DictionaryElement;

@Service
public class ApiService {
    private final TranslationService translationService;
    private final AdditionalDataService additionalDataService;

    public ApiService(@Autowired TranslationService translationService, AdditionalDataService additionalDataService) {
        this.translationService = translationService;
        this.additionalDataService = additionalDataService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish) {
        String translation = translationService.getTranslationFromApi(wordInEnglish);
        Map additionalData = additionalDataService.getTranslationFromApi(wordInEnglish);
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
