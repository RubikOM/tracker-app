package com.rubinskyi.controller.translator;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.dto.DictionaryElementDto;
import com.rubinskyi.service.api.TranslationFromApiService;

@RestController
@PropertySource("classpath:validationMessages.properties")
public class TranslationFromApiController {
    @Value("${response.isEmpty}")
    private String noResultFoundMessage;
    private final TranslationFromApiService translationFromApiService;

    @Autowired
    public TranslationFromApiController(TranslationFromApiService translationFromApiService) {
        this.translationFromApiService = translationFromApiService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElementDto getDictionaryElementFromApi(@PathVariable String wordInEnglish, Principal principal) {
        String validWord = makeWordValidForApi(wordInEnglish);
        DictionaryElement dictionaryElementFromApi = translationFromApiService.getDictionaryElementFromApi(validWord, principal);

        if (dictionaryElementFromApi.getTranslation() == null || dictionaryElementFromApi.getTranslation().isEmpty()) {
            return new DictionaryElementDto(wordInEnglish, noResultFoundMessage);
        } else return new DictionaryElementDto(dictionaryElementFromApi);
    }

    private String makeWordValidForApi(String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }
}
