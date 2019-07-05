package com.rubinskyi.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.rubinskyi.pojo.apiEntity.ComprehensiveElementLingvo;
import com.rubinskyi.pojo.mapper.ComprehensiveElementMapperSimple;
import com.rubinskyi.service.api.ComprehensiveTranslationService;
import com.rubinskyi.service.api.DictionaryElementConsolidatorService;

@Service
@PropertySource("classpath:api.properties")
public class ComprehensiveTranslationServiceLingvo implements ComprehensiveTranslationService {

    @Value("${comprehensiveDataCall}")
    private String API_CALL_TEMPLATE_COMPREHENSIVE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialTranslationServiceLingvo.class);
    private final ComprehensiveElementMapperSimple comprehensiveElementMapper;
    private final DictionaryElementConsolidatorService dictionaryElementConsolidatorService;

    @Autowired
    public ComprehensiveTranslationServiceLingvo(ComprehensiveElementMapperSimple comprehensiveElementMapper,
                                                 DictionaryElementConsolidatorService dictionaryElementConsolidatorService) {
        this.comprehensiveElementMapper = comprehensiveElementMapper;
        this.dictionaryElementConsolidatorService = dictionaryElementConsolidatorService;
    }

    public DictionaryElement obtainDataFromApi(String wordInEnglish, User user) {
        String apiCall = String.format(API_CALL_TEMPLATE_COMPREHENSIVE, makeWordValidForApi(wordInEnglish));
        List<DictionaryElement> dictionaryElements = collectDictionaryElements(apiCall, user);
        if (dictionaryElements.isEmpty()) {
            DictionaryElement emptyResponse = new DictionaryElement();
            emptyResponse.setWord(wordInEnglish);
            return emptyResponse;
        }
        return dictionaryElementConsolidatorService.consolidateDictionaryElements(dictionaryElements);
    }

    private List<DictionaryElement> collectDictionaryElements(@NotNull String apiCall, User user) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<ComprehensiveElementLingvo> elements;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            if (responseEntity.getBody().equals("null")) return new ArrayList<>();
            elements = mapper.readValue(jsonInput, new TypeReference<List<ComprehensiveElementLingvo>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }

        return mapToDictionaryElementList(elements, user);
    }

    private List<DictionaryElement> mapToDictionaryElementList(List<ComprehensiveElementLingvo> lingvoList, User user) {
        return lingvoList.parallelStream()
                .filter(a -> isInterestedToUser(a, user))
                .sorted(new SortByUserInterests(user))
                .map(comprehensiveElementMapper::comprehensiveElementToDictionaryElement)
                .collect(Collectors.toList());
    }

    // TODO this method should be replaced to some other class
    // ".contains()" looks very ugly and unreadable, change Vocabulary names in DB, must be equals() here and split method, not inline
    private boolean isInterestedToUser(ComprehensiveElementLingvo comprehensiveElement, @NotNull User user) {
        for (Interest interest : user.getInterests()) {
            if (comprehensiveElement.getDictionaryName().contains(interest.getDictionary().getName())) return true;

        }
        return false;
    }

    // TODO this looks bad af, need logic change here
    private String makeWordValidForApi(@NotNull String word) {
        String[] wordAsArray = word.split(" ");
        return wordAsArray.length > 0 ? wordAsArray[wordAsArray.length - 1] : word;
    }

    // TODO try to refactor this one to use lambdas, not Comparator
    private static final class SortByUserInterests implements Comparator<ComprehensiveElementLingvo> {
        private final User user;

        private SortByUserInterests(User user) {
            this.user = user;
        }

        @Override
        public int compare(ComprehensiveElementLingvo comprehensiveElement1, ComprehensiveElementLingvo comprehensiveElement2) {
            return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
        }

        private int calculateValue(@NotNull ComprehensiveElementLingvo comprehensiveElement) {
            String dictionary = comprehensiveElement.getDictionaryName();
            Integer result = user.getInterests()
                    .stream()
                    .filter(interest -> dictionary.contains(interest.getDictionary().getName()))
                    .findAny()
                    .map(Interest::getPriority)
                    .orElseThrow(() -> new RuntimeException("Can't compare dictionaries which are not in users interests!"));

            return result;
        }
    }
}
