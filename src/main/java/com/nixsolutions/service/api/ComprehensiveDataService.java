package com.nixsolutions.service.api;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class ComprehensiveDataService {
    @Value("${apiCall}")
    private String API_CALL_TEMPLATE_ADDITIONAL;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialDataService.class);

    public Map obtainDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_ADDITIONAL, wordInEnglish);

        List<TutorCard> tutorCards = mapJsonToTutorCards(apiCall);
        List<TutorCard> tutorCardFiltered = tutorCards.stream().
                filter(tutorCard -> isInterestedToUser(tutorCard, user))
                .sorted(new SortByDictionary())
                .collect(Collectors.toList());

        return extract(tutorCardFiltered);
    }

    private List<TutorCard> mapJsonToTutorCards(@NotNull String apiCall) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<TutorCard> elements;

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

    // TODO this method should be renamed
    private Map<String, String> extract(List<TutorCard> cards) {
        Map<String, String> result = new HashMap<>();
        for (TutorCard tutorCard : cards) {
            String examples = tutorCard.getExamples();
            String transcription = tutorCard.getTranscription();

            if (!transcription.isEmpty() && !result.containsKey("transcription")) {
                result.put("transcription", transcription);
            }
            if (!examples.isEmpty()) {
                return addMapToMap(extractExampleFromString(examples), result);
            }
        }
        return new HashMap<>();
    }

    private Map<String, String> extractExampleFromString(String examplesAsString) {
        Map<String, String> result = new HashMap<>();
        String[] examples = examplesAsString.split("—|\\r?\\n");
        result.put("example", examples[0]);
        result.put("exampleTranslation", examples[1]);
        return result;
    }

    private boolean isInterestedToUser(TutorCard tutorCard, @NotNull User user) {
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
