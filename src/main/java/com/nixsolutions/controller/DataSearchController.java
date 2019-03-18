package com.nixsolutions.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.api.Minicard;

// TODO rename it
@RestController
public class DataSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSearchController.class);
    private final String ENGLISH_LANGUAGE = "1033";
    private final String RUSSIAN_LANGUAGE = "1049";
    private final String REQUEST_MICICARD = "Minicard";
    private final String APP_UNIQ_ID = "Basic NGUzZGZiMTktZWRjNS00ZTEzLWFkZmEtMTY3NDE0YTQ3YTYxOjk0YjQ0MzcwOGY1ZjRjNTE4NjU3M2U2NjM0MWQ1Mzc3";

    @GetMapping("/fillPage/{word}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String word) {
        // TODO try not to readToString, but instantly in the mapper.readValue();
        String jsonInput = getDictionaryElementFromApi(word);

        Minicard element = new Minicard();
        ObjectMapper mapper = new ObjectMapper();
        try {
            element = mapper.readValue(jsonInput, Minicard.class);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            // TODO something here
        }
        Minicard.Translation translation = element.getTranslation();

        return new DictionaryElement.Builder(word, translation.getTranslations()).build();
    }

    // TODO move to service
    // TODO refactor
    // TODO POST for key if 401
    // TODO is empty in Minicard
    private String getDictionaryElementFromApi(String word) {
        String apiCall = "https://api.lingvolive.com/Translation/" + REQUEST_MICICARD + "?text=" + word
                + "&srcLang=" + ENGLISH_LANGUAGE + "&dstLang=" + RUSSIAN_LANGUAGE;

        String todayKey = "ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmxlSEFpT2pFMU5USTVPREEzT1RVc0lrMXZaR1ZzSWpwN0lrTm9ZWEpoWTNSbGNuTlFaW" +
                "EpFWVhraU9qVXdNREF3TENKVmMyVnlTV1FpT2pJeE9EY3NJbFZ1YVhGMVpVbGtJam9pTkdVelpHWmlNVGt0WldSak5TMDBaVEV6TFdGa1ptRXRNVFkzTkRFMFlUUTNZVFl4" +
                "SW4xOS41azN2YWQ1VFZCNGliTnpmZ2FCbk9VQ2tVR0E5OXdHNWd3c21OamxfNlYw";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(todayKey));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.exchange(apiCall, HttpMethod.GET, entity, String.class).getBody();
    }
}
