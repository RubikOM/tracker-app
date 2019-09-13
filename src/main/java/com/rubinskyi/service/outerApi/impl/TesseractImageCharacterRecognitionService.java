package com.rubinskyi.service.outerApi.impl;

import com.rubinskyi.service.outerApi.ImageCharacterRecognitionService;
import com.rubinskyi.util.file.FileSearcher;
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
    private final FileSearcher fileSearcher;
    private static final String TESSERACT_MACHINE_LEARNING_DATA_FOLDER_NAME = "tessdata";
    // TODO не попадаю в папку с тессдатой, хоть в дебаге попадаю, надо откатиться назад и посмотреть что поменялось в пути к файлу
    // TODO мб проблема была в том что попадал в папку build которая залочена

    @Override
    @LogExecutionTime
    public String resolveImage(File file) {
        Tesseract tesseract = new Tesseract();
        String recognisedText;
        try {
            String tessdataFolder = fileSearcher.getFilePathByName(TESSERACT_MACHINE_LEARNING_DATA_FOLDER_NAME);
            tesseract.setDatapath(tessdataFolder);
            tesseract.setLanguage("eng");
            recognisedText = tesseract.doOCR(file);
        } catch (Exception e) {
            log.error("Error during image recognition", e);
            return EMPTY_RESPONSE;
        }
        return recognisedText;
    }
}
