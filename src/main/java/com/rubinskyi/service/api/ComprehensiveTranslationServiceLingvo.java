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
import com.rubinskyi.pojo.api.ComprehensiveElementLingvo;

@Service
@PropertySource("classpath:api.properties")
public class ComprehensiveTranslationServiceLingvo implements ComprehensiveTranslationService {
    @Value("${comprehensiveDataCall}")
    private String API_CALL_TEMPLATE_COMPREHENSIVE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialTranslationServiceLingvo.class);

    public DictionaryElement obtainDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_COMPREHENSIVE, makeWordValidForApi(wordInEnglish));

        List<ComprehensiveElementLingvo> comprehensiveElements = mapJsonToTutorCards(apiCall);
        if (comprehensiveElements == null) return new DictionaryElement();
        List<ComprehensiveElementLingvo> comprehensiveElementsFiltered = comprehensiveElements.stream().
                filter(comprehensiveElement -> isInterestedToUser(comprehensiveElement, user))
                .sorted(new SortByDictionary(user))
                .collect(Collectors.toList());

        return mapToDictionaryElement(comprehensiveElementsFiltered, user);
    }

    private List<ComprehensiveElementLingvo> mapJsonToTutorCards(@NotNull String apiCall) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<ComprehensiveElementLingvo> elements;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            elements = mapper.readValue(jsonInput, new TypeReference<List<ComprehensiveElementLingvo>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }
        return elements;
    }

    // TODO move this to Mapper class
    private DictionaryElement mapToDictionaryElement(@NotNull List<ComprehensiveElementLingvo> wordCards, User author) {
        DictionaryElement result = new DictionaryElement();
        for (ComprehensiveElementLingvo comprehensiveElement : wordCards) {
            // TODO this need to be refactored
            result.setAuthor(author);
            mapWord(comprehensiveElement, result);
            mapExampleAndExampleTranslation(comprehensiveElement, result);
            mapTranscription(comprehensiveElement, result);
            mapTranslation(comprehensiveElement, result);
            if (!result.getExample().equals("") && !result.getTranscription().equals("")) return result;
        }
        return result;
    }

    // TODO rename this method
    private void mapWord(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                         DictionaryElement result) {
        // TODO optionals here
        if ((result.getWord() == null || result.getWord().isEmpty()) && !comprehensiveElement.getHeading().isEmpty()) {
            result.setWord(comprehensiveElement.getHeading());
        }
    }

    // TODO FIRSTLY WRITE FCIN TESTS FOR THIS METHOD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void mapExampleAndExampleTranslation(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                                 DictionaryElement result) {
        String example = "";
        String exampleTranslation = "";
        // TODO change to optionals

        if ((result.getExample() == null || result.getExample().isEmpty())
                && !comprehensiveElement.getExamples().isEmpty()) {
            String examplesAsString = comprehensiveElement.getExamples();
            String[] examples = examplesAsString.split("—|\\r?\\n");
            if (examples.length >= 2) {
                example = examples[0].trim();
                exampleTranslation = examples[1].trim();
            }
        }
        result.setExample(example);
        result.setExampleTranslation(exampleTranslation);
    }

    private void mapTranscription(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                  DictionaryElement result) {
        String transcription = comprehensiveElement.getTranscription();
        // TODO change to optionals
        if ((result.getTranscription() == null || result.getTranscription().isEmpty()) && !transcription.isEmpty()) {
            result.setTranscription(transcription);
        }
    }

    private void mapTranslation(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                DictionaryElement result) {
        String translation = comprehensiveElement.getTranslations();
        // TODO change to optionals
        if ((result.getTranslation() == null || result.getTranslation().isEmpty()) && !translation.isEmpty()) {
            result.setTranslation(translation);
        }
    }

    private boolean isInterestedToUser(ComprehensiveElementLingvo comprehensiveElement, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (comprehensiveElement.getDictionaryName().contains(interest.getDictionary().getName())) return true;
        }
        return false;
    }

    // TODO this method throws to api just 1 word, need some normal logic here
    private String makeWordValidForApi(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }

    class SortByDictionary implements Comparator<ComprehensiveElementLingvo> {
        private User user;

        SortByDictionary(User user) {
            this.user = user;
        }

        @Override
        public int compare(ComprehensiveElementLingvo comprehensiveElement1, ComprehensiveElementLingvo comprehensiveElement2) {
            return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
        }

        private int calculateValue(@NotNull ComprehensiveElementLingvo comprehensiveElement) {
            String dictionary = comprehensiveElement.getDictionaryName();
            for (Interest interest : user.getInterests()) {
                if (dictionary.contains(interest.getDictionary().getName())) {
                    return interest.getPriority();
                }
            }
            throw new RuntimeException("Can't compare dictionaries which are not in users interests!");
        }
    }
}
