package com.nixsolutions.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.ElementFromApi;

// TODO rename it
@RestController
public class DataSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSearchController.class);
    private final String ENGLISH_LANGUAGE = "1033";
    private final String RUSSIAN_LANGUAGE = "1049";
    private final String REQUEST_MICICARD = "Minicard";

    @GetMapping("/fillPage/{word}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String word) {
        // TODO try not to readToString, but instantly in the mapper.readValue();
        String jsonInput = getDictionaryElementFromApi(word);

        ElementFromApi element;
        ObjectMapper mapper = new ObjectMapper();
        ElementFromApi.Translation translation = null;
        try {
            element = mapper.readValue(jsonInput, ElementFromApi.class);
            translation = element.getTranslation();
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            // TODO something here
        }

        return new DictionaryElement.Builder(word, translation.getLingvoTranslations()).build();
    }

    private String getDictionaryElementFromApi(String word) {
        String apiCall = "https://api.lingvolive.com/Translation/" + REQUEST_MICICARD + "?text=" + word
                + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE + "&returnJsonArticles=true";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(apiCall, String.class);
    }
}
