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
                .sorted(new SortByDictionary(user))
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
    private DictionaryElement mapToDictionaryElement(@NotNull List<ComprehensiveElement> wordCards) {
        DictionaryElement result = new DictionaryElement();
        for (ComprehensiveElement comprehensiveElement : wordCards) {
            // TODO this need to be refactored
            mapWord(comprehensiveElement, result);
            mapExampleAndExampleTranslation(comprehensiveElement, result);
            mapTranscription(comprehensiveElement, result);
            mapTranslation(comprehensiveElement, result);
            if (!result.getExample().equals("") && !result.getTranscription().equals("")) return result;
        }
        return result;
    }

    private void mapWord(@NotNull ComprehensiveElement comprehensiveElement,
                         DictionaryElement result) {
        // TODO optionals here
        if (result.getWord() == null || result.getWord().isEmpty()) {
            result.setWord(comprehensiveElement.getHeading());
        }
    }

    // TODO refactor this method
    // TODO this method is kinda more global than I thought
    // TODO I'm retarded AF, provide normal fcin changes.

    // TODO FIRSTLY WRITE FCIN TESTS FOR THIS METHOD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void mapExampleAndExampleTranslation(@NotNull ComprehensiveElement comprehensiveElement,
                                                 DictionaryElement result) {
        String example = "";
        String exampleTranslation = "";
        // TODO change to optionals

        if ((result.getExample() == null || result.getExample().isEmpty())
                && !comprehensiveElement.getExamples().isEmpty()) {
            String examplesAsString = comprehensiveElement.getExamples();
            String[] examples = examplesAsString.split("—|\\r?\\n");
            if (examples.length >= 2) {
                example = examples[0];
                exampleTranslation = examples[1];
            }
        }
        result.setExample(example);
        result.setExampleTranslation(exampleTranslation);
    }

    private void mapTranscription(@NotNull ComprehensiveElement comprehensiveElement,
                                  DictionaryElement result) {
        String transcription = comprehensiveElement.getTranscription();
        // TODO change to optionals
        if (result.getTranscription() == null || result.getTranscription().isEmpty()) {
            result.setTranscription(transcription);
        } else result.setTranscription("");
    }

    private void mapTranslation(@NotNull ComprehensiveElement comprehensiveElement,
                                DictionaryElement result) {
        String translation = comprehensiveElement.getTranslations();
        // TODO change to optionals
        if (translation.isEmpty()) {
            result.setTranslation(translation);
        } else result.setTranslation("");
    }

    private boolean isInterestedToUser(ComprehensiveElement comprehensiveElement, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (comprehensiveElement.getDictionaryName().contains(interest.getDictionary().getName())) return true;
        }
        return false;
    }

    // TODO this is the SHIT number 2 so far
    // TODO this method throws to api just 1 word, need some normal logic here
    // TODO rename this method
    private String makeWordValidToUrl(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }

    class SortByDictionary implements Comparator<ComprehensiveElement> {
        private User user;

        SortByDictionary(User user) {
            this.user = user;
        }

        @Override
        public int compare(ComprehensiveElement comprehensiveElement1, ComprehensiveElement comprehensiveElement2) {
            return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
        }

        private int calculateValue(@NotNull ComprehensiveElement comprehensiveElement) {
            String dictionary = comprehensiveElement.getDictionaryName();
            for (Interest interest : user.getInterests()) {
                if (dictionary.equals(interest)) return interest.getPriority();
            }
            return -1;
        }
    }
}
