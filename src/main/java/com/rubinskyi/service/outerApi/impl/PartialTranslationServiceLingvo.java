package com.rubinskyi.service.outerApi.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.bean.properties.ApiProperties;
import com.rubinskyi.pojo.lingvo.PartialElementLingvo;
import com.rubinskyi.service.outerApi.PartialTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartialTranslationServiceLingvo implements PartialTranslationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiProperties apiProperties;

    public String obtainTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(apiProperties.getApiCallTemplatePartial(), makeWordValidToUrl(wordInEnglish));

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
