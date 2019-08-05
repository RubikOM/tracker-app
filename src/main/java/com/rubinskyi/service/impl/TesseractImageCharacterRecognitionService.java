package com.rubinskyi.service.impl;

import com.rubinskyi.service.ImageCharacterRecognitionService;
import net.sourceforge.tess4j.Tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@PropertySource("classpath:characterRecognition.properties")
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    @Value("classpath:tessdata")
    private File tesseractTrainedModelsFolder;
    @Value("${emptyResponse}")
    private String emptyResponseMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractImageCharacterRecognitionService.class);

    @Override
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String recognisedText;
        try {
            tesseract.setDatapath(tesseractTrainedModelsFolder.getAbsolutePath());
            tesseract.setLanguage("eng");
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            LOGGER.error("Error during image recognition", e);
            return emptyResponseMessage;
        }
        return recognisedText;
    }
}
