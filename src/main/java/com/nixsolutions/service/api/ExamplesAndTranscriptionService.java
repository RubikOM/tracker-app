package com.nixsolutions.service.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.pojo.api.TutorCard;

// TODO renameItLater
@Service
public class ExamplesAndTranscriptionService {
    @Value("${apiCall}")
    private String API_CALL_TEMPLATE_FULL;
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationService.class);

    public List<TutorCard> getTranslationFromApi(String wordInEnglish) {
        RestTemplate restTemplate = new RestTemplate();
        String apiCall = String.format(API_CALL_TEMPLATE_FULL, wordInEnglish);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        return mapJsonToTutorCards(responseEntity);
    }

    private List<TutorCard> mapJsonToTutorCards(@NotNull ResponseEntity<String> responseEntity) {
        List<TutorCard> elements = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        String jsonInput = responseEntity.getBody();
        try {
            elements = mapper.readValue(jsonInput, new TypeReference<List<TutorCard>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return elements;

        // TODO how to map list of entities??? 1) Parse String to list of strings and parse to array 2) should be some instrument
    }
}
