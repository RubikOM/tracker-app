package com.nixsolutions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.nixsolutions.entity.DictionaryElement;

@Service
@PropertySource(value = {"classpath:api.properties"})
public class ApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    private final TranslationApiService translationApiService;

    public ApiService(@Autowired TranslationApiService translationApiService) {
        this.translationApiService = translationApiService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish) {
        String translation = translationApiService.getTranslationFromApi(wordInEnglish);

        return new DictionaryElement.Builder(wordInEnglish, translation).build();
    }


}
