package com.rubinskyi.service.api;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
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
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import com.rubinskyi.pojo.api.ComprehensiveElement;

@Service
@PropertySource("classpath:api.properties")
public class ComprehensiveDataServiceLingvo implements ComprehensiveDataService {
    @Value("${comprehensiveDataCall}")
    private String API_CALL_TEMPLATE_COMPREHENSIVE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialDataServiceLingvo.class);

    public DictionaryElement obtainDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_COMPREHENSIVE, makeWordValidToUrl(wordInEnglish));

        List<ComprehensiveElement> comprehensiveElements = mapJsonToTutorCards(apiCall);
        if (comprehensiveElements == null) return new DictionaryElement();
        List<ComprehensiveElement> comprehensiveElementsFiltered = comprehensiveElements.stream().
                filter(comprehensiveElement -> isInterestedToUser(comprehensiveElement, user))
                .sorted(new SortByDictionary())
                .collect(Collectors.toList());

        return mapToDictionaryElement(comprehensiveElementsFiltered);
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

    // TODO refactor this method
    // TODO move this to Mapper class
    // TODO set a word!!!!!!
    private DictionaryElement mapToDictionaryElement(@NotNull List<ComprehensiveElement> wordCards) {
        DictionaryElement result = new DictionaryElement();
        for (ComprehensiveElement comprehensiveElement : wordCards) {
            mapExampleAndExampleTranslation(comprehensiveElement, result);
            mapTranscription(comprehensiveElement, result);
            mapTranslation(comprehensiveElement, result);
            if (!result.getExample().isEmpty() && !result.getTranscription().isEmpty()) return result;
        }
        return result;
    }

    // TODO refactor this method
    // TODO this method is kinda more global than I thought
    private void mapExampleAndExampleTranslation(@NotNull ComprehensiveElement comprehensiveElement,
                                                 DictionaryElement result) {
        result.setWord(comprehensiveElement.getHeading());
        if (!comprehensiveElement.getExamples().isEmpty()) {
            String examplesAsString = comprehensiveElement.getExamples();
            String[] examples = examplesAsString.split("—|\\r?\\n");
            if (examples.length >= 2) {
                result.setExample(examples[0]);
                result.setExampleTranslation(examples[1]);
            }
        }
    }

    private void mapTranscription(@NotNull ComprehensiveElement comprehensiveElement,
                                  DictionaryElement result) {
        String transcription = comprehensiveElement.getTranscription();
        if (!transcription.isEmpty()) {
            result.setTranscription(transcription);
        }
    }

    private void mapTranslation(@NotNull ComprehensiveElement comprehensiveElement,
                                DictionaryElement result) {
        String translation = comprehensiveElement.getTranslations();
        if (!translation.isEmpty()) {
            result.setTranslation(translation);
        }
    }

    private boolean isInterestedToUser(ComprehensiveElement comprehensiveElement, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (comprehensiveElement.getDictionaryName().contains(interest.getDictionary().getName())) return true;
        }
        return false;
    }

    // TODO this is the SHIT number 2 so far
    // TODO this method throws to api just 1 word, need some normal logic here
    private String makeWordValidToUrl(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }

    // TODO this is the biggest SHIT so far
    // TODO LingvoComputer < LingvoUniversal  < Learning for "Mike", change order of sorting
    class SortByDictionary implements Comparator<ComprehensiveElement> {
        @Override
        public int compare(ComprehensiveElement comprehensiveElement1, ComprehensiveElement comprehensiveElement2) {
            return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
        }

        // TODO check WTF is this, user has his own interests, why hardcode???!!!
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
