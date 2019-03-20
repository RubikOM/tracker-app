package com.nixsolutions.service.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@PropertySource(value = {"classpath:api.properties"})

///// LAST COMMIT ON API V1

public class CommonApiService {
    private final static String ENGLISH_LANGUAGE = "1033";
    private final static String RUSSIAN_LANGUAGE = "1049";
    public final static String REQUEST_TYPE_MINI = "Minicard";
    public final static String REQUEST_TYPE_FULL = "Translation";
    public final static String API_CALL_TEMPLATE = "https://developers.lingvolive.com/api/v1/" + "%s" + "?text=" + "%s"
            + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;
    @Value("${authorizationUrl}")
    private String AUTHORIZATION_URL;

    private final Environment environment;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonApiService.class);

    @Autowired
    public CommonApiService(@NotNull Environment environment) {
        this.environment = environment;
    }

    public ResponseEntity<String> getResponseFromApi(String urlForCall) {
        ResponseEntity<String> responseEntity = performHttpGetCall(urlForCall);
        if (responseEntity.getStatusCodeValue() != 200) {
            refreshTodaySecretKey();
            responseEntity = performHttpGetCall(urlForCall);
        }
        return responseEntity;
    }

    private ResponseEntity<String> performHttpGetCall(String urlForCall) {
        HttpHeaders headersGet = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headersGet.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(getValueFromProperties("todayKey")));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headersGet);

        return restTemplate.exchange(urlForCall, HttpMethod.GET, entity, String.class);
    }

    // TODO investigate this method because it looks like not working
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

    private String getValueFromProperties(String key) {
        return environment.getRequiredProperty(key);
    }
}
