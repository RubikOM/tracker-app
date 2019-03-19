package com.nixsolutions.pojo.api;

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

// TODO rename this class
@Component
@PropertySource(value = {"classpath:api.properties"})
public class ApiCommonService {
    private final static String ENGLISH_LANGUAGE = "1033";
    private final static String RUSSIAN_LANGUAGE = "1049";
    public final static String REQUEST_TYPE_MINI = "Minicard";
    public final static String REQUEST_TYPE_FULL = "Translate";
    public final static String API_CALL_TEMPLATE = "https://api.lingvolive.com/Translation/" + "%s" + "?text=" + "%s"
            + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;
    @Value("${authorizationUrl}")
    private String AUTHORIZATION_URL;

    private final Environment environment;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCommonService.class);

    @Autowired
    public ApiCommonService(@NotNull Environment environment) {
        this.environment = environment;
    }

    public ResponseEntity getResponseAsString(String apiCall) {
        HttpHeaders headersGet = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headersGet.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(getValueFromProperties("todayKey")));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headersGet);

        return restTemplate.exchange(apiCall, HttpMethod.GET, entity, String.class);
    }

    // TODO investigate this method because it looks like not working
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
