package com.nixsolutions.service.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.entity.Interest;
import com.nixsolutions.entity.User;
import com.nixsolutions.pojo.api.TutorCard;

@Service
public class AdditionalDataService {
    @Value("${apiCall}")
    private String API_CALL_TEMPLATE_ADDITIONAL;
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationService.class);

    public Map getTranslationFromApi(String wordInEnglish, User user) {
        RestTemplate restTemplate = new RestTemplate();
        String apiCall = String.format(API_CALL_TEMPLATE_ADDITIONAL, wordInEnglish);
        Map<String, String> result = new HashMap<>();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        List<TutorCard> tutorCards = mapJsonToTutorCards(responseEntity);
        for (TutorCard tutorCard : tutorCards) {
            if (!tutorCard.getTranscription().isEmpty()) {
                result.put("transcription", tutorCard.getTranscription());
            }
            for (Interest interest : user.getInterests())
                if (tutorCard.getDictionaryName().contains(interest.getDictionary().getName())) {
                    // TODO Lots of optimisation must be here
                    if (!tutorCard.getExamples().isEmpty()) {
                        String[] examples = tutorCard.getExamples().split("—|\\r?\\n");
                        result.put("example", examples[0]);
                        result.put("exampleTranslation", examples[1]);

                        if (result.containsKey("transcription") && result.containsKey("example")) return result;
                    }
                }
        }
        return result;
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
    }
}
