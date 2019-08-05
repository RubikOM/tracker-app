package com.rubinskyi.service.impl;

import com.rubinskyi.service.ImageCharacterRecognitionService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractImageCharacterRecognitionService.class);

    @Override
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String text;
        try {
            tesseract.setDatapath("src/main/resources/tessdata");
            text = tesseract.doOCR(file);
        } catch (TesseractException e) {
            // TODO this should be in controller layer
            LOGGER.error("Error during image recognition", e);
            // TODO to properties
            return "emptyMessage";
        }
        return text;
    }
}
