package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:api.properties")
@Getter
public class ApiProperties {
    @Value("${partialDataCall}")
    private String apiCallTemplatePartial;
    @Value("${comprehensiveDataCall}")
    private String apiCallTemplateComprehensive;
    @Value("${sentenceApiCall}")
    private String apiCallTemplateSentence;
    @Value("${multiWordThreadPoolSize}")
    private int multiWordThreadPoolSize;
    @Value("${lingvoThreadPoolSize}")
    private int lingvoThreadPoolSize;
    @Value("${suggestedWords}")
    private int suggestedWordsAmount;
}
