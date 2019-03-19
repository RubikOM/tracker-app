package com.nixsolutions.service;

import static com.nixsolutions.pojo.api.ApiCommonService.API_CALL_TEMPLATE;
import static com.nixsolutions.pojo.api.ApiCommonService.REQUEST_TYPE_MINI;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.pojo.api.ApiCommonService;
import com.nixsolutions.pojo.api.Minicard;

@Service
public class TranslationApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationApiService.class);
    private final ApiCommonService apiCommonService;

    @Autowired
    public TranslationApiService(ApiCommonService apiCommonService) {
        this.apiCommonService = apiCommonService;
    }

    public String getTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE, REQUEST_TYPE_MINI, wordInEnglish);

        ResponseEntity<String> responseEntity = apiCommonService.getResponseAsString(apiCall);
        if (responseEntity.getStatusCodeValue() != 200) {
            apiCommonService.refreshTodaySecretKey();
            responseEntity = apiCommonService.getResponseAsString(apiCall);
        }

        Minicard minicard = mapJsonToMinicard(responseEntity);
        return minicard.getTranslation().getTranslations();
    }

    private Minicard mapJsonToMinicard(@NotNull ResponseEntity<String> responseEntity) {
        Minicard element;
        ObjectMapper mapper = new ObjectMapper();

        String jsonInput = responseEntity.getBody();
        try {
            element = mapper.readValue(jsonInput, Minicard.class);
            return element;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            return new Minicard();
        }
    }
}
