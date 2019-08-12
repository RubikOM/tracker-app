package com.rubinskyi.service.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.config.properties.ApiProperties;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import com.rubinskyi.pojo.lingvo.ComprehensiveElementLingvo;
import com.rubinskyi.pojo.mapper.ComprehensiveElementMapperSimple;
import com.rubinskyi.service.api.ComprehensiveTranslationService;
import com.rubinskyi.service.api.DictionaryElementConsolidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComprehensiveTranslationServiceLingvo implements ComprehensiveTranslationService {
    private final ComprehensiveElementMapperSimple comprehensiveElementMapper;
    private final DictionaryElementConsolidatorService dictionaryElementConsolidatorService;
    // TODO created session - scoped user bean??
    private User currentUser;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private ApiProperties apiProperties;

    @Autowired
    public ComprehensiveTranslationServiceLingvo(ComprehensiveElementMapperSimple comprehensiveElementMapper,
                                                 DictionaryElementConsolidatorService dictionaryElementConsolidatorService,
                                                 RestTemplate restTemplate, ObjectMapper objectMapper, ApiProperties apiProperties) {
        this.comprehensiveElementMapper = comprehensiveElementMapper;
        this.dictionaryElementConsolidatorService = dictionaryElementConsolidatorService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.apiProperties = apiProperties;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish, User user) {
        currentUser = user;
        String apiCall = String.format(apiProperties.getApiCallTemplateComprehensive(), wordInEnglish);
        List<DictionaryElement> dictionaryElements = collectDictionaryElements(apiCall, user);
        if (dictionaryElements.isEmpty()) {
            DictionaryElement emptyResponse = new DictionaryElement();
            emptyResponse.setWord(wordInEnglish);
            return emptyResponse;
        }
        return dictionaryElementConsolidatorService.consolidateDictionaryElements(dictionaryElements);
    }

    private List<DictionaryElement> collectDictionaryElements(String apiCall, User user) {
        List<ComprehensiveElementLingvo> elements;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            if (jsonInput == null || jsonInput.equals("null")) return new ArrayList<>();
            elements = objectMapper.readValue(jsonInput, new TypeReference<List<ComprehensiveElementLingvo>>() {
            });
        } catch (IOException e) {
            log.error("Can't map JSON to ComprehensiveElementLingvo list ", e);
            throw new RuntimeException(e);
        }

        return mapToDictionaryElementList(elements, user);
    }

    private List<DictionaryElement> mapToDictionaryElementList(List<ComprehensiveElementLingvo> lingvoList, User user) {
        List<DictionaryElement> dictionaryElements = lingvoList.stream()
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
