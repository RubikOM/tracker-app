package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:api.properties")
public class OcrProperties {
    @Value("${emptyResponse}")
    private String emptyResponseMessage;
    @Value("${wrongFileType}")
    private String wrongFileFormatMessage;
    @Value("${cannotRecogniseCharacters}")
    private String cannotRecogniseCharactersMessage;
}
