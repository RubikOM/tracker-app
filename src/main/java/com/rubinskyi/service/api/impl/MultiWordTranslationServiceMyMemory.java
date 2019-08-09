package com.rubinskyi.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinskyi.pojo.apiEntity.RussianSentenceResponse;
import com.rubinskyi.pojo.apiEntity.SentenceElementMyMemory;
import com.rubinskyi.service.api.MultiWordTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:api.properties")
public class MultiWordTranslationServiceMyMemory implements MultiWordTranslationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiWordTranslationServiceMyMemory.class);
    private static final int MAX_STRING_LENGTH = 500;
    private static final String EMPTY_RESPONSE = "";
    @Value("${sentenceApiCall}")
    private String API_CALL_TEMPLATE_SENTENCE;
    @Value("${multiWordThreadPoolSize}")
    private int MULTI_WORD_THREAD_POOL_SIZE;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MultiWordTranslationServiceMyMemory(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String translateSentenceToRussian(String englishSentence) {
        if (englishSentence == null || englishSentence.isEmpty()) return EMPTY_RESPONSE;
        List<String> sentences = cropStringBySentences(englishSentence);
        if (sentences.isEmpty()) return EMPTY_RESPONSE;
        String sentence = sentences.get(0);

        SentenceElementMyMemory multiWordTranslationFromApi = getMultiWordTranslationFromApi(sentence);
        return multiWordTranslationFromApi.getResponseData().getTranslatedText();
    }

    @Override
    public String translateTextToRussian(String englishText) {
        if (englishText == null || englishText.isEmpty()) return EMPTY_RESPONSE;
        List<String> sentences = cropStringBySentences(englishText);
        if (sentences.isEmpty()) return EMPTY_RESPONSE;
        List<Future> futures = new ArrayList<>();

        for (String sentence : sentences) {
            ExecutorService executorService = Executors.newFixedThreadPool(MULTI_WORD_THREAD_POOL_SIZE);
            Future<SentenceElementMyMemory> elementFuture = executorService.submit(() -> getMultiWordTranslationFromApi(sentence));
            futures.add(elementFuture);
        }

        String result = futures.stream()
                .map(this::extractFromFuture)
                .filter(sentenceElement -> sentenceElement.getResponseData() != null)
                .map(SentenceElementMyMemory::getResponseData)
                .map(RussianSentenceResponse::getTranslatedText)
                .collect(Collectors.joining(" "));
        return result;
    }

    private SentenceElementMyMemory getMultiWordTranslationFromApi(String englishText) {
        SentenceElementMyMemory element;
        String apiCall = String.format(API_CALL_TEMPLATE_SENTENCE, englishText);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiCall, String.class);
        String jsonInput = responseEntity.getBody();
        try {
            if (jsonInput == null || jsonInput.equals("null")) return new SentenceElementMyMemory();
            element = objectMapper.readValue(jsonInput, SentenceElementMyMemory.class);
        } catch (IOException e) {
            LOGGER.error("Can't map JSON to ComprehensiveElementLingvo list ", e);
            throw new RuntimeException(e);
        }
        return element;
    }

    private List<String> cropStringBySentences(String initialText) {
        Pattern pattern = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)",
                Pattern.MULTILINE | Pattern.COMMENTS);
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
        String delimiter = " ";
        for (int i = 0; i < result.size() - 1; i++) {
            String currentElement = result.get(i);
            String nextElement = result.get(i + 1);

            if (currentElement.length() + nextElement.length() < MAX_STRING_LENGTH - delimiter.length()) {
                result.set(i, currentElement.concat(delimiter).concat(nextElement));
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
            LOGGER.error("Error while retrieving future value ", e);
            return new SentenceElementMyMemory();
        }
    }
}
