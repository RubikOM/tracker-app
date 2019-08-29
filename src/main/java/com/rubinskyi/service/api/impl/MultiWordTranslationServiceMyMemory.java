package com.rubinskyi.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.bean.properties.ApiProperties;
import com.rubinskyi.pojo.sentences.RussianSentenceResponse;
import com.rubinskyi.pojo.sentences.SentenceElementMyMemory;
import com.rubinskyi.service.api.MultiWordTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Service
@Slf4j
@RequiredArgsConstructor
public class MultiWordTranslationServiceMyMemory implements MultiWordTranslationService {
    private static final int MAX_STRING_LENGTH = 500;
    private static final String REGEX_TO_SPLIT_BY_SEPARATE_SENTENCES = "[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ExecutorService multiWordExecutorService;
    private final ApiProperties apiProperties;

    @Override
    public String translateSentenceToRussian(String englishSentence) {
        if (englishSentence == null || englishSentence.isEmpty()) return EMPTY;
        List<String> sentences = cropStringBySentences(englishSentence);
        if (sentences.isEmpty()) return EMPTY;
        String sentence = sentences.get(0);

        SentenceElementMyMemory multiWordTranslationFromApi = getMultiWordTranslationFromApi(sentence);
        return multiWordTranslationFromApi.getResponseData().getTranslatedText();
    }

    @Override
    public String translateTextToRussian(String englishText) {
        if (englishText == null || englishText.isEmpty()) return EMPTY;
        List<String> sentences = cropStringBySentences(englishText);
        if (sentences.isEmpty()) return EMPTY;
        List<Future> futures = new ArrayList<>();

        for (String sentence : sentences) {
            Future<SentenceElementMyMemory> elementFuture = multiWordExecutorService.submit(() -> getMultiWordTranslationFromApi(sentence));
            futures.add(elementFuture);
        }

        String russianTranslation = futures.stream()
                .map(this::extractFromFuture)
                .map(SentenceElementMyMemory::getResponseData)
                .map(RussianSentenceResponse::getTranslatedText)
                .collect(Collectors.joining(SPACE));
        return russianTranslation;
    }

    private SentenceElementMyMemory getMultiWordTranslationFromApi(String englishText) {
        SentenceElementMyMemory element;
        String apiCall = String.format(apiProperties.getApiCallTemplateSentence(), englishText);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            if (jsonInput == null || jsonInput.equals("null")) return new SentenceElementMyMemory();
            element = objectMapper.readValue(jsonInput, SentenceElementMyMemory.class);
        } catch (IOException e) {
            log.error("Can't map JSON to ComprehensiveElementLingvo list ", e);
            throw new RuntimeException(e);
        }
        return element;
    }

    private List<String> cropStringBySentences(String initialText) {
        Pattern pattern = Pattern.compile(REGEX_TO_SPLIT_BY_SEPARATE_SENTENCES, Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(initialText);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            result.add(group);
        }
        return mergeStrings(result);
    }

    // TODO replace with streams to increase readability
    private List<String> mergeStrings(List<String> sentences) {
        ArrayList<String> result = new ArrayList<>(sentences);
        for (int i = 0; i < result.size() - 1; i++) {
            String currentElement = result.get(i);
            String nextElement = result.get(i + 1);

            if (currentElement.length() + nextElement.length() < MAX_STRING_LENGTH - SPACE.length()) {
                result.set(i, currentElement.concat(SPACE).concat(nextElement));
                result.remove(i + 1);
                i--;
            }
        }
        return result;
    }

    private SentenceElementMyMemory extractFromFuture(Future future) {
        try {
            return (SentenceElementMyMemory) future.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error while retrieving future value ", e);
            return new SentenceElementMyMemory();
        }
    }
}
