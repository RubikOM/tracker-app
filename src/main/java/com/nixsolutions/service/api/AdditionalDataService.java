package com.nixsolutions.service.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    // TODO rename everything here
    public Map getDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_ADDITIONAL, wordInEnglish);
        Map<String, String> result = new HashMap<>();
        List<TutorCard> tutorCardFiltered = new ArrayList<>();

        List<TutorCard> tutorCards = mapJsonToTutorCards(apiCall);
        for (TutorCard tutorCard : tutorCards) {
            if (IsInterestedToUser(tutorCard, user)) {
                tutorCardFiltered.add(tutorCard);
            }
            if (!tutorCard.getTranscription().isEmpty() && !result.containsKey("transcription")) {
                result.put("transcription", tutorCard.getTranscription());
            }
        }
        tutorCardFiltered.sort(new SortByDictionary());
        Map<String, String> map = getExampleAndItsTranslation(tutorCardFiltered);
        return addMapToMap(map, result);
    }

    private List<TutorCard> mapJsonToTutorCards(@NotNull String apiCall) {
        RestTemplate restTemplate = new RestTemplate();
        List<TutorCard> elements;
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            elements = mapper.readValue(jsonInput, new TypeReference<List<TutorCard>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }
        return elements;
    }

    private Map<String, String> getExampleAndItsTranslation(List<TutorCard> cards) {
        for (TutorCard tutorCard : cards) {
            String examples = tutorCard.getExamples();
            if (!examples.isEmpty()) {
                return extractFromString(examples);
            }
        }
        return new HashMap<>();
    }

    private Map<String, String> extractFromString(String examplesAsString) {
        Map<String, String> result = new HashMap<>();
        String[] examples = examplesAsString.split("—|\\r?\\n");
        result.put("example", examples[0]);
        result.put("exampleTranslation", examples[1]);
        return result;
    }

    private boolean IsInterestedToUser(TutorCard tutorCard, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (tutorCard.getDictionaryName().contains(interest.getDictionary().getName())) return true;
        }
        return false;
    }

    private Map<String, String> addMapToMap(Map<String, String> mapToAdd, Map<String, String> mapToReceive) {
        mapToAdd.forEach(mapToReceive::putIfAbsent);
        return mapToReceive;
    }

    class SortByDictionary implements Comparator<TutorCard> {
        @Override
        public int compare(TutorCard tutorCard1, TutorCard tutorCard2) {
            return calculateValue(tutorCard1) - calculateValue(tutorCard2);
        }

        private int calculateValue(TutorCard tutorCard) {
            String dictionary = tutorCard.getDictionaryName();
            if (dictionary.contains("Learning")) {
                return 1;
            } else if (dictionary.contains("Universal")) {
                return 2;
            } else return 3;
        }
    }
}
