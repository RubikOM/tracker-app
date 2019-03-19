package com.nixsolutions.pojo.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// TODO rename this class
@Component
public class DataHolder {
    private static final String ENGLISH_LANGUAGE = "1033";
    private static final String RUSSIAN_LANGUAGE = "1049";
    private static final String AUTHORIZATION_URL = "https://developers.lingvolive.com/api/v1.1/authenticate";
    public static final String API_CALL_TEMPLATE = "https://api.lingvolive.com/Translation/%s?text=%s" +
            "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;
    public static final String REQUEST_TYPE_MINICARD = "Minicard";
    public static final String REQUEST_TYPE_TRANSLATION = "Translate";

    private final Environment environment;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataHolder.class);

    @Autowired
    public DataHolder(Environment environment) {
        this.environment = environment;
    }

    public ResponseEntity getResponseAsString(String apiCall) {
        HttpHeaders headersGet = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headersGet.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(getValueFromProperties("todayKey")));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headersGet);

        return restTemplate.exchange(apiCall, HttpMethod.GET, entity, String.class);
    }

    public void refreshTodaySecretKey() {
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

    private String getValueFromProperties(String key) {
        return environment.getRequiredProperty(key);
    }
}
