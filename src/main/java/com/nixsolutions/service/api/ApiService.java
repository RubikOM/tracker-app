package com.nixsolutions.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.entity.DictionaryElement;

// TODO rename all this services
@Service
public class ApiService {
    private final TranslationService translationService;
    private final PhoneticService phoneticService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    public ApiService(@Autowired TranslationService translationService, PhoneticService phoneticService) {
        this.translationService = translationService;
        this.phoneticService = phoneticService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish) {
        String translation = translationService.getTranslationFromApi(wordInEnglish);
        String phonetic = phoneticService.getPhoneticFromApi(wordInEnglish);

        return new DictionaryElement.Builder(wordInEnglish, translation).transcription(phonetic).build();
    }
}
