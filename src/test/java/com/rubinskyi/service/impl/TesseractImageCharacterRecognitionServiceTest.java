package com.rubinskyi.service.impl;

import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.config.TextFileReader;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class TesseractImageCharacterRecognitionServiceTest {

    @Autowired
    private TesseractImageCharacterRecognitionService tesseractImageCharacterRecognitionService;
    @Autowired
    TextFileReader textFileReader;

    @Test
    public void resolveImage_shouldReturnRightLargeText() {
        File file = new File("src/test/resources/tessimage/large_image.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file).replace("\n", SPACE).trim();
        String expectedResult = textFileReader.getContentByFileName("tesseractTestData.txt");

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldReturnRightSmallText() {
        File file = new File("src/test/resources/tessimage/small_image.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "Noisy image\n" +
                "to test\n" +
                "Tesseract OCR\n";

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldReturnRightNoisyText() {
        File file = new File("src/test/resources/tessimage/image_noisy.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "~ Tesseract Will\n" +
                "Fail With Noisy\n" +
                "- Backgrounds\n";

        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_shouldResolveAllCharacters() {
        File file = new File("src/test/resources/tessimage/eurotext.png");

        String expectedResult = textFileReader.getContentByFileName("allCharacters.txt");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file).replace("\n", SPACE).trim();
        assertEquals(expectedResult.trim(), result);
    }

    @Test
    public void resolveImage_realWorldImage_realBook() {
        File file = new File("src/test/resources/tessimage/javaBook.png");

        String expectedResult = textFileReader.getContentByFileName("realBook.txt");
        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        assertEquals(ignoreSpaces(expectedResult), ignoreSpaces(result));
    }

    @Test
    @Ignore
    public void resolveImage_shouldResolveHandWrittenText() {
        File file = new File("src/test/resources/tessimage/hand_written_text.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "05221859";

        assertEquals(expectedResult, result);
    }

    private String ignoreSpaces(String initialString) {
        return initialString.replaceAll("\\s+", "");
    }
}