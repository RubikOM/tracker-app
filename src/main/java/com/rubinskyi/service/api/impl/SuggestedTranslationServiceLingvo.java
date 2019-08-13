package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.properties.ApiProperties;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.api.ComprehensiveTranslationService;
import com.rubinskyi.service.api.SuggestedTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SuggestedTranslationServiceLingvo implements SuggestedTranslationService {
    private final ExecutorService lingvoExecutorService;
    private final ComprehensiveTranslationService comprehensiveTranslationService;
    private final ApiProperties apiProperties;

    @Override
    public List<DictionaryElement> getSuggestedElements(String englishText, User currentUser) {
        // TODO this whole thing should be rebuilt
        String[] cleanedWords = splitToArray(englishText);
        List<String> wordsToSuggest = Arrays.stream(cleanedWords)
                .distinct()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .limit(apiProperties.getSuggestedWordsAmount())
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return getDictionaryElements(wordsToSuggest, currentUser);
    }

    private List<DictionaryElement> getDictionaryElements(List<String> wordsToSuggest, User currentUser) {
        List<Future> futures = new ArrayList<>();

        for (String word : wordsToSuggest) {
            Future<DictionaryElement> elementFuture = lingvoExecutorService.submit(
                    () -> comprehensiveTranslationService.getDictionaryElementFromApi(word, currentUser));
            futures.add(elementFuture);
        }
        List<DictionaryElement> dictionaryElementsToSuggest = futures.stream()
                .map(this::extractFromFuture)
//                .filter(element -> element.getTranslation() != null)
                .collect(Collectors.toList());

        return dictionaryElementsToSuggest;
    }

    private DictionaryElement extractFromFuture(Future future) {
        try {
            return (DictionaryElement) future.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error while retrieving future value ", e);
            return new DictionaryElement();
        }
    }

    private String[] splitToArray(String englishText) {
        String[] cleanedArray = englishText.replaceAll("\n", SPACE)
                .replace(".", SPACE)
                .replace("-", SPACE)
                .replaceAll("[A-Z]([A-Z0-9]*[a-z][a-z0-9]*[A-Z]|[a-z0-9]*[A-Z][A-Z0-9]*[a-z])[A-Za-z0-9]*", SPACE)
                .replaceAll("[a-z]([a-z0-9]*[A-Z][A-Z0-9]*[a-z]|[A-Z0-9]*[a-z][a-z0-9]*[A-Z])[a-zA-Z0-9]*", SPACE)
                .replaceAll("[()?:!.,;{}]+", SPACE)
                .split(SPACE);

        return cleanedArray;
    }
}
