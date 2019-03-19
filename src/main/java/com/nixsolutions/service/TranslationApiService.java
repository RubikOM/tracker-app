package com.nixsolutions.service;

import static com.nixsolutions.pojo.api.DataHolder.API_CALL_TEMPLATE;
import static com.nixsolutions.pojo.api.DataHolder.REQUEST_TYPE_MINICARD;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.pojo.api.DataHolder;
import com.nixsolutions.pojo.api.Minicard;

@Service
public class TranslationApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationApiService.class);
    private final DataHolder dataHolder;

    @Autowired
    public TranslationApiService(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    // TODO return not dictionary element, but translation
    public String getTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE, REQUEST_TYPE_MINICARD, wordInEnglish);

        ResponseEntity<String> responseEntity = dataHolder.getResponseAsString(apiCall);
        if (responseEntity.getStatusCodeValue() != 200) {
            dataHolder.refreshTodaySecretKey();
            responseEntity = dataHolder.getResponseAsString(apiCall);
        }

        Minicard minicard = mapJsonToMinicard(responseEntity);
        return minicard.getTranslation().getTranslations();
    }

    // TODO rename stuff
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
