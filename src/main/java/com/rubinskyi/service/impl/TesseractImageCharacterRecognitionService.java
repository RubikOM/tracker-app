package com.rubinskyi.service.impl;

import com.rubinskyi.config.properties.OcrProperties;
import com.rubinskyi.service.ImageCharacterRecognitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    private final OcrProperties ocrProperties;

    @Override
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String recognisedText;
        try {
            tesseract.setDatapath(ocrProperties.getTesseractTrainedModelsFolder().getAbsolutePath());
            tesseract.setLanguage("eng");
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            log.error("Error during image recognition", e);
            return ocrProperties.getEmptyResponseMessage();
        }
        return recognisedText;
    }
}
