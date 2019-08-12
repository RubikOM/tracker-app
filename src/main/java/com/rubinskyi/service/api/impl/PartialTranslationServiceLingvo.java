package com.rubinskyi.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.pojo.lingvo.PartialElementLingvo;
import com.rubinskyi.service.api.PartialTranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@PropertySource("classpath:api.properties")
@Slf4j
public class PartialTranslationServiceLingvo implements PartialTranslationService {
    @Value("${partialDataCall}")
    private String API_CALL_TEMPLATE_PARTIAL;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PartialTranslationServiceLingvo(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String obtainTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE_PARTIAL, makeWordValidToUrl(wordInEnglish));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        PartialElementLingvo partialElement = mapJsonToMinicard(responseEntity);
        return partialElement.getTranslation() == null ? "" : partialElement.getTranslation().getTranslations();
    }

    private PartialElementLingvo mapJsonToMinicard(ResponseEntity<String> responseEntity) {
        PartialElementLingvo element;

        String jsonInput = responseEntity.getBody();
        try {
            element = objectMapper.readValue(jsonInput, PartialElementLingvo.class);
            return element;
        } catch (IOException e) {
            log.error(e.toString(), e);
            return new PartialElementLingvo();
        }
    }

    private String makeWordValidToUrl(String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }
}
