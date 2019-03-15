package com.nixsolutions.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.entity.DictionaryElement;

// TODO rename it
@RestController
public class DataSearchController {
    private final String ENGLISH_LANGUAGE = "1033";
    private final String RUSSIAN_LANGUAGE = "1049";


    @GetMapping("/fillPage/{word}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String word) {
        String jsonInput = getDictionaryElementFromApi(word);
        Map<String, Object> response = null;
        try {
            response = new ObjectMapper().readValue(jsonInput, HashMap.class);
        } catch (IOException e) {
            // NOP
            // TODO something here
        }
        Map<String, String> translation = (Map<String, String>) response.get("translation");
        String result = translation.get("lingvoTranslations");

        return new DictionaryElement.Builder(word, result).build();
    }

    private String getDictionaryElementFromApi(String word) {
        String API_CALL = "https://api.lingvolive.com/Translation/Minicard?text=" + word +
                "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE + "&returnJsonArticles=true";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(API_CALL, String.class);

        return result;
    }
}
