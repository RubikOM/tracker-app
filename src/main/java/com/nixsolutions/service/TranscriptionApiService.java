package com.nixsolutions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.pojo.api.ApiCommonService;

@Service
public class TranscriptionApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionApiService.class);
    private final ApiCommonService apiCommonService;

    @Autowired
    public TranscriptionApiService(ApiCommonService apiCommonService) {
        this.apiCommonService = apiCommonService;
    }
}
