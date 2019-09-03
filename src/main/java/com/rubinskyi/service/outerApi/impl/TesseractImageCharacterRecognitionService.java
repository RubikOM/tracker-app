package com.rubinskyi.service.outerApi.impl;

import com.rubinskyi.service.outerApi.ImageCharacterRecognitionService;
import com.rubinskyi.bean.FileSearcherBean;
import com.rubinskyi.util.profiling.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.rubinskyi.pojo.constant.StringConstant.EMPTY_RESPONSE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TesseractImageCharacterRecognitionService implements ImageCharacterRecognitionService {
    private final FileSearcherBean fileSearcher;
    private static final String TESSERACT_TRAINED_DATA_FOLDER = "tessdata";

    @Override
    @LogExecutionTime
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String recognisedText;
        try {
            tesseract.setDatapath(fileSearcher.getFileByName(TESSERACT_TRAINED_DATA_FOLDER).getAbsolutePath());
            tesseract.setLanguage("eng");
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            log.error("Error during image recognition", e);
            return EMPTY_RESPONSE;
        }
        return recognisedText;
    }
}
