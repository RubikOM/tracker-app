package com.rubinskyi.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Getter
@PropertySource("classpath:characterRecognition.properties")
public class OcrProperties {
    @Value("${ocr.emptyResponse}")
    private String emptyResponseMessage;
    @Value("${ocr.wrongFileType}")
    private String wrongFileFormatMessage;
    @Value("${ocr.cannotRecogniseCharacters}")
    private String cannotRecogniseCharactersMessage;
    @Value("${ocr.tessimageFolder}")
    private File userUploadedImagesFolder;
    @Value("${ocr.tessdataFolder}")
    private File tesseractTrainedModelsFolder;
}
