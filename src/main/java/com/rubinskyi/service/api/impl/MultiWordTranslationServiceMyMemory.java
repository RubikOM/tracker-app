package com.rubinskyi.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.pojo.apiEntity.RussianSentenceResponse;
import com.rubinskyi.pojo.apiEntity.SentenceElementMyMemory;
import com.rubinskyi.service.api.MultiWordTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:api.properties")
public class MultiWordTranslationServiceMyMemory implements MultiWordTranslationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiWordTranslationServiceMyMemory.class);
    private static final int MAX_STRING_LENGTH = 500;
    @Value("${sentenceApiCall}")
    private String API_CALL_TEMPLATE_SENTENCE;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MultiWordTranslationServiceMyMemory(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // TODO 500 max length logic and parallel
    @Override
    public String translateSentenceToRussian(String englishText) {
        String apiCall = String.format(API_CALL_TEMPLATE_SENTENCE, englishText);
        SentenceElementMyMemory element;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();

        try {
            if (jsonInput.equals("null")) return "";
            element = objectMapper.readValue(jsonInput, SentenceElementMyMemory.class);
        } catch (IOException e) {
            LOGGER.error("Can't map JSON to ComprehensiveElementLingvo list ", e);
            throw new RuntimeException(e);
        }
        RussianSentenceResponse responseData = element.getResponseData();

        return responseData.getTranslatedText();
    }

    // TODO here another split : not by 1 sentence, but by nearest to 500 characters sentences
    private List<String> cropIntoPieces(String uncroppedString) {
        String[] split = uncroppedString.split("/(\\.)/");
        return Arrays.asList(split);
    }
}
