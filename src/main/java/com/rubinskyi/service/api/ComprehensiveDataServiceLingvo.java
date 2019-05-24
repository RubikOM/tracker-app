package com.rubinskyi.service.api;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import com.rubinskyi.pojo.api.ComprehensiveElement;

@Service
@PropertySource("classpath:api.properties")
public class ComprehensiveDataServiceLingvo implements ComprehensiveDataService {
    @Value("${comprehensiveDataCall}")
    private String API_CALL_TEMPLATE_COMPREHENSIVE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialDataServiceLingvo.class);

    public Map obtainDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_COMPREHENSIVE, makeWordValidToUrl(wordInEnglish));

        List<ComprehensiveElement> comprehensiveElements = mapJsonToTutorCards(apiCall);
        if (comprehensiveElements == null) return new HashMap();
        List<ComprehensiveElement> comprehensiveElementFiltered = comprehensiveElements.stream().
                filter(comprehensiveElement -> isInterestedToUser(comprehensiveElement, user))
                .sorted(new SortByDictionary())
                .collect(Collectors.toList());

        return extract(comprehensiveElementFiltered);
    }

    private List<ComprehensiveElement> mapJsonToTutorCards(@NotNull String apiCall) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<ComprehensiveElement> elements;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            elements = mapper.readValue(jsonInput, new TypeReference<List<ComprehensiveElement>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }
        return elements;
    }

    private Map<String, String> extract(@NotNull List<ComprehensiveElement> cards) {
        Map<String, String> result = new HashMap<>();
        for (ComprehensiveElement comprehensiveElement : cards) {
            if (!result.containsKey("transcription")) {
                Map<String, String> transcriptionMap = extractTranscription(comprehensiveElement);
                addMapToMap(transcriptionMap, result);
            }
            if (!result.containsKey("example")) {
                Map<String, String> exampleMap = extractExample(comprehensiveElement);
                addMapToMap(exampleMap, result);
            }
            if (result.containsKey("transcription") && result.containsKey("example")) return result;
        }
        return result;
    }

    private Map<String, String> extractExample(@NotNull ComprehensiveElement comprehensiveElement) {
        String examplesAsString = comprehensiveElement.getExamples();
        Map<String, String> exampleMap = new HashMap<>();
        String[] examples = examplesAsString.split("—|\\r?\\n");
        if (examples.length >= 2) {
            exampleMap.put("example", examples[0]);
            exampleMap.put("exampleTranslation", examples[1]);
        }
        return exampleMap;
    }

    private Map<String, String> extractTranscription(@NotNull ComprehensiveElement comprehensiveElement) {
        String transcription = comprehensiveElement.getTranscription();
        Map<String, String> transcriptionMap = new HashMap<>();
        if (!transcription.isEmpty()) {
            transcriptionMap.put("transcription", transcription);
        }
        return transcriptionMap;
    }

    private boolean isInterestedToUser(ComprehensiveElement comprehensiveElement, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (comprehensiveElement.getDictionaryName().contains(interest.getDictionary().getName())) return true;
        }
        return false;
    }

    private void addMapToMap(@NotNull Map<String, String> mapToAdd, @NotNull Map<String, String> mapToReceive) {
        mapToAdd.forEach(mapToReceive::putIfAbsent);
    }

    private String makeWordValidToUrl(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }

    class SortByDictionary implements Comparator<ComprehensiveElement> {
        @Override
        public int compare(ComprehensiveElement comprehensiveElement1, ComprehensiveElement comprehensiveElement2) {
            return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
        }

        private int calculateValue(@NotNull ComprehensiveElement comprehensiveElement) {
            String dictionary = comprehensiveElement.getDictionaryName();
            if (dictionary.contains("Learning")) {
                return 1;
            } else if (dictionary.contains("Universal")) {
                return 2;
            } else return 3;
        }
    }
}
