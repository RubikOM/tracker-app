package com.nixsolutions.service.api;

import static com.nixsolutions.service.api.CommonApiService.getResponseFromApi;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.pojo.api.Minicard;

@Service
@PropertySource(value = {"classpath:api.properties"})
public class TranslationService {
    @Value("${minicardCall}")
    private String API_CALL_TEMPLATE_MINICARD;
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationService.class);

    public String getTranslationFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE_MINICARD, wordInEnglish);

        ResponseEntity<String> responseEntity = getResponseFromApi(apiCall);
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
