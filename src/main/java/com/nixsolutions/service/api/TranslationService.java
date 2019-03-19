package com.nixsolutions.service.api;

import static com.nixsolutions.service.api.CommonApiService.API_CALL_TEMPLATE;
import static com.nixsolutions.service.api.CommonApiService.REQUEST_TYPE_MINI;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.pojo.api.Minicard;

@Service
public class TranslationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationService.class);
    private final CommonApiService commonApiService;

    @Autowired
    public TranslationService(CommonApiService commonApiService) {
        this.commonApiService = commonApiService;
    }

    public String getTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE, REQUEST_TYPE_MINI, wordInEnglish);

        ResponseEntity<String> responseEntity = commonApiService.getResponseFromApi(apiCall);
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
