package com.nixsolutions.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource(value = {"classpath:api.properties"})
public class TranslationApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationApiService.class);
    private static final String ENGLISH_LANGUAGE = "1033";
    private static final String RUSSIAN_LANGUAGE = "1049";
    private static final String REQUEST_TYPE_MINICARD = "Minicard";
    private static final String AUTHORIZATION_URL = "https://developers.lingvolive.com/api/v1.1/authenticate";

    private final Environment environment;

    public TranslationApiService(@Autowired Environment environment) {
        this.environment = environment;
    }

    public DictionaryElement getDictionaryElementFromApi(String word) {
        String apiCall = "https://api.lingvolive.com/Translation/" + REQUEST_TYPE_MINICARD + "?text=" + word
                + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;

        ResponseEntity<String> responseEntity = getApiString(apiCall);
        if (responseEntity.getStatusCodeValue() != 200) {
            refreshTodaySecretKey();
            responseEntity = getApiString(apiCall);
        }

        return new DictionaryElement.Builder(word, getTranslationFromResponse(responseEntity)).build();
    }

    private String getTranslationFromResponse(@NotNull ResponseEntity<String> responseEntity) {
        Minicard element;
        ObjectMapper mapper = new ObjectMapper();

        String jsonInput = responseEntity.getBody();
        try {
            element = mapper.readValue(jsonInput, Minicard.class);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            return "";
            // TODO some alert or something in controller if translation is empty
        }
        return element.getTranslation().getTranslations();
    }

    private String getValueFromProperties(String key) {
        return environment.getRequiredProperty(key);
    }

    private ResponseEntity getApiString(String apiCall) {
        HttpHeaders headersGet = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headersGet.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(getValueFromProperties("todayKey")));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headersGet);

        return restTemplate.exchange(apiCall, HttpMethod.GET, entity, String.class);
    }

    private void refreshTodaySecretKey() {
        HttpHeaders headersPost = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headersPost.add(HttpHeaders.AUTHORIZATION, getValueFromProperties("apiKey"));
        HttpEntity<String> entityPost = new HttpEntity<>("parameters", headersPost);
        String secretKey = restTemplate.postForObject(AUTHORIZATION_URL, entityPost, String.class);

        writeToApiProperties("todayKey", secretKey);
    }

    private void writeToApiProperties(String key, String value) {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream("api.properties")) {
            prop.setProperty(key, value);
            prop.store(output, null);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }
}
