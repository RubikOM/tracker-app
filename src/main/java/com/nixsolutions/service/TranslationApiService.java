package com.nixsolutions.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.api.Minicard;

@Service
public class TranslationApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationApiService.class);
    private final String ENGLISH_LANGUAGE = "1033";
    private final String RUSSIAN_LANGUAGE = "1049";
    private final String REQUEST_MICICARD = "Minicard";
    private final String AUTHORIZATION_URL = "https://developers.lingvolive.com/api/v1.1/authenticate";

    // TODO this looks little weird, mb take it from props as well (looks totally good)
    private String todayKey = "";

    public DictionaryElement getDictionaryElementFromApi(String word) {
        String apiCall = "https://api.lingvolive.com/Translation/" + REQUEST_MICICARD + "?text=" + word
                + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headersGet = new HttpHeaders();
        Minicard element;
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> exchange = getApiString(apiCall, restTemplate, headersGet);

        if (exchange.getStatusCodeValue() != 200) {
            refreshTodaySecretKey(restTemplate);
            exchange = getApiString(apiCall, restTemplate, headersGet);
        }

        String jsonInput = exchange.getBody();
        try {
            element = mapper.readValue(jsonInput, Minicard.class);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            return new DictionaryElement();
            // TODO some alert or something to controller
        }
        Minicard.Translation translation = element.getTranslation();
        return new DictionaryElement.Builder(word, translation.getTranslations()).build();
    }

    private void refreshTodaySecretKey(@NotNull RestTemplate restTemplate) {
        HttpHeaders headersPost = new HttpHeaders();

        headersPost.add(HttpHeaders.AUTHORIZATION, getApiKey());
        HttpEntity<String> entityPost = new HttpEntity<>("parameters", headersPost);
        todayKey = restTemplate.postForObject(AUTHORIZATION_URL, entityPost, String.class);
    }

    private ResponseEntity getApiString(String apiCall, @NotNull RestTemplate restTemplate, @NotNull HttpHeaders headersGet) {
        headersGet.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(todayKey));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headersGet);
        ResponseEntity<String> exchange = restTemplate.exchange(apiCall, HttpMethod.GET, entity, String.class);
        return exchange;
    }

    private String getApiKey() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            return prop.getProperty("apiKey");
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }
}
