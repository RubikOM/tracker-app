package com.rubinskyi.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
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
    // TODO created session - scoped user bean??
    private User currentUser;

    @Autowired
    public ComprehensiveTranslationServiceLingvo(ComprehensiveElementMapperSimple comprehensiveElementMapper,
                                                 DictionaryElementConsolidatorService dictionaryElementConsolidatorService) {
        this.comprehensiveElementMapper = comprehensiveElementMapper;
        this.dictionaryElementConsolidatorService = dictionaryElementConsolidatorService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, User user) {
        currentUser = user;
        String apiCall = String.format(API_CALL_TEMPLATE_COMPREHENSIVE, wordInEnglish);
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
        List<DictionaryElement> dictionaryElements = lingvoList.parallelStream()
                .filter(elementLingvo -> user.interestedInDictionary(elementLingvo.getDictionaryName()))
                .sorted(this::compare)
                .map(comprehensiveElementMapper::comprehensiveElementToDictionaryElement)
                .collect(Collectors.toList());
        return dictionaryElements;
    }

    private int compare(ComprehensiveElementLingvo comprehensiveElement1, ComprehensiveElementLingvo comprehensiveElement2) {
        return calculateValue(comprehensiveElement1) - calculateValue(comprehensiveElement2);
    }

    private int calculateValue(ComprehensiveElementLingvo comprehensiveElement) {
        String dictionary = comprehensiveElement.getDictionaryName();
        Integer result = currentUser.getInterests()
                .stream()
                .filter(interest -> dictionary.equals(interest.getDictionary().getName()))
                .findAny()
                .map(Interest::getPriority)
                .orElseThrow(() -> new RuntimeException("Can't compare dictionaries which are not in users interests!"));

        return result;
    }
}
