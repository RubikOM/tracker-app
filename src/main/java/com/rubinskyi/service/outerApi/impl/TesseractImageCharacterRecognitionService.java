package com.rubinskyi.service.outerApi.impl;

import com.rubinskyi.service.outerApi.ImageCharacterRecognitionService;
import com.rubinskyi.util.profiling.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.rubinskyi.pojo.constant.StringConstant.EMPTY_RESPONSE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    private final Tesseract tesseract;

    @Override
    @LogExecutionTime
    public String resolveImage(File file) {
        String recognisedText;
        try {
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            log.error("Error during image recognition", e);
            return EMPTY_RESPONSE;
        }
        return recognisedText;
    }
}
