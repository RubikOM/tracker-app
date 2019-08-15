package com.rubinskyi.service.impl;

import com.rubinskyi.service.ImageCharacterRecognitionService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
//@PropertySource("classpath:characterRecognition.properties")
@Slf4j
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    @Value("classpath:tessdata")
    private File tesseractTrainedModelsFolder;
//    @Value("${emptyResponse}")
//    private String emptyResponseMessage;

    @Override
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String recognisedText;
        try {
            tesseract.setDatapath(tesseractTrainedModelsFolder.getAbsolutePath());
            tesseract.setLanguage("eng");
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            log.error("Error during image recognition", e);
            return "emptyResponseMessage";
        }
        return recognisedText;
    }
}
