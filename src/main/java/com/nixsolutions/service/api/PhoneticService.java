package com.nixsolutions.service.api;

import static com.nixsolutions.service.api.CommonApiService.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PhoneticService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneticService.class);
    private final CommonApiService commonApiService;

    @Autowired
    public PhoneticService(CommonApiService commonApiService) {
        this.commonApiService = commonApiService;
    }

    public String getPhoneticFromApi(String wordInEnglish) {
        String apiCall = String.format(API_CALL_TEMPLATE, REQUEST_TYPE_FULL, wordInEnglish);

        // TODO redo this with regularExpressions or streams!!!
        // TODO gave to rebuild it
        ResponseEntity<String> responseEntity = commonApiService.getResponseFromApi(apiCall);
        String body = responseEntity.getBody();
        int positionOfTranscription = body.indexOf("Transcription");
        String transcription = body.substring(positionOfTranscription + 24, positionOfTranscription + 100);
        String result = transcription.substring(0, transcription.indexOf("\",\""));
        return "[" + result + "]";
    }
}
