package com.nixsolutions.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class CommonApiService {
    @Value("${apiCall}")
    public static String API_CALL_TEMPLATE_FULL;


    public static ResponseEntity<String> getResponseFromApi(String urlForCall) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(urlForCall, String.class);
    }
}
