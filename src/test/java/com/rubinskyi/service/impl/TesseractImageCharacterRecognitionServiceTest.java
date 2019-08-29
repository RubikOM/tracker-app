package com.rubinskyi.service.impl;

import com.rubinskyi.testBean.TextFileReaderBean;
import com.rubinskyi.config.SpringTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringTestConfig.class})
public class TesseractImageCharacterRecognitionServiceTest {

    @Autowired
    private TesseractImageCharacterRecognitionService tesseractImageCharacterRecognitionService;
    @Autowired
    private TextFileReaderBean textFileReaderBean;

    @Test
    public void resolveImage_shouldReturnRightLargeText() {
        File file = textFileReaderBean.getFileByName("tessimage/large_image.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file).replace("\n", SPACE).trim();
        String expectedResult = textFileReaderBean.getContentByFileName("ocrTextFiles/tesseractTestData.txt");

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldReturnRightSmallText() {
        File file = textFileReaderBean.getFileByName("tessimage/small_image.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "Noisy image\n" +
                "to test\n" +
                "Tesseract OCR\n";

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldReturnRightNoisyText() {
        File file = textFileReaderBean.getFileByName("tessimage/image_noisy.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "~ Tesseract Will\n" +
                "Fail With Noisy\n" +
                "- Backgrounds\n";

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldResolveAllCharacters() {
        File file = textFileReaderBean.getFileByName("tessimage/eurotext.png");

        String expectedResult = textFileReaderBean.getContentByFileName("ocrTextFiles/allCharacters.txt");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file).replace("\n", SPACE).trim();
        assertEquals(expectedResult.trim(), result);
    }

    @Test
    public void resolveImage_realWorldImage_realBook() {
        File file = textFileReaderBean.getFileByName("tessimage/javaBook.png");

        String expectedResult = textFileReaderBean.getContentByFileName("ocrTextFiles/realBook.txt");
        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        assertEquals(ignoreSpaces(expectedResult), ignoreSpaces(result));
    }

    private String ignoreSpaces(String initialString) {
        return initialString.replaceAll("\\s+", EMPTY);
    }
}