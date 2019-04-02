package com.rubinskyi.service.api;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.pojo.api.PartialElement;

@Service
@PropertySource("classpath:api.properties")
public class PartialDataService {
    @Value("${partialDataCall}")
    private String API_CALL_TEMPLATE_PARTIAL;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialDataService.class);

    public String obtainTranslationFromApi(String wordInEnglish) {
        RestTemplate restTemplate = new RestTemplate();
        String apiCall = String.format(API_CALL_TEMPLATE_PARTIAL, makeWordValidToUrl(wordInEnglish));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        PartialElement partialElement = mapJsonToMinicard(responseEntity);
        return partialElement.getTranslation().getTranslations();
    }

    private PartialElement mapJsonToMinicard(@NotNull ResponseEntity<String> responseEntity) {
        PartialElement element;
        ObjectMapper mapper = new ObjectMapper();

        String jsonInput = responseEntity.getBody();
        try {
            element = mapper.readValue(jsonInput, PartialElement.class);
            return element;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            return new PartialElement();
        }
    }

    private String makeWordValidToUrl(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }
}
