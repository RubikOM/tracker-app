package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:api.properties")
public class ApiProperties {
    @Value("${api.partialDataCall}")
    private String apiCallTemplatePartial;
    @Value("${api.comprehensiveDataCall}")
    private String apiCallTemplateComprehensive;
    @Value("${api.sentenceApiCall}")
    private String apiCallTemplateSentence;
    @Value("${api.multiWordThreadPoolSize}")
    private int multiWordThreadPoolSize;
    @Value("${api.lingvoThreadPoolSize}")
    private int lingvoThreadPoolSize;
    @Value("${api.suggestedWordsAmount}")
    private int suggestedWordsAmount;
}
