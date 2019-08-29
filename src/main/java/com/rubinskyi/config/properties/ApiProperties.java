package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Getter
@PropertySource("classpath:application.properties")
public class ApiProperties {
    @Value("${api.partialApiCall}")
    private String apiCallTemplatePartial;
    @Value("${api.comprehensiveApiCall}")
    private String apiCallTemplateComprehensive;
    @Value("${api.sentenceApiCall}")
    private String apiCallTemplateSentence;
    @Value("${api.suggestedWordsAmount}")
    private int suggestedWordsAmount;
    @Value("${api.translationsAmount}")
    private int translationsAmount;
    @Value("${api.examplesAmount}")
    private int examplesAmount;

    @Value("${ocr.wrongFileType}")
    private String wrongFileFormatMessage;
    @Value("${ocr.cannotRecogniseCharacters}")
    private String cannotRecogniseCharactersMessage;
    // TODO rebuild pathes to files later
    @Value("${ocr.tessimageFolder}")
    private File userUploadedImagesFolder;
    @Value("${ocr.tessdataFolder}")
    private File tesseractTrainedModelsFolder;
}
