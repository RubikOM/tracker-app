package com.rubinskyi.service.impl;

import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.config.SpringTestConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class, HibernateConfig.class})
public class TesseractImageCharacterRecognitionServiceTest {

    @Autowired
    private TesseractImageCharacterRecognitionService tesseractImageCharacterRecognitionService;

    @Test
    public void resolveImage_shouldReturnRightLargeText() {
        File file = new File("src/test/resources/tessimage/large_image.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "PREREQUISITES\n" +
                "\n" +
                "In order to make the most of this, you will need to have\n" +
                "a little bit of programming experience. All examples in this\n" +
                "book are in the Python programming language. Familiarity\n" +
                "with Python or other scripting languages is suggested, but\n" +
                "not required.\n" +
                "\n" +
                "You'll also need to know some basic mathematics. This\n" +
                "book is hands-on and example driven: lots of examples and\n" +
                "lots of code, so even if your math skills are not up to par,\n" +
                "do not worry! The examples are very detailed and heavily\n" +
                "documented to help you follow along.\n";

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
    // TODO clean unused data from response (i.e. ~ -)
    public void resolveImage_shouldReturnRightNoisyText() {
        File file = new File("src/test/resources/tessimage/image_noisy.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "~ Tesseract Will\n" +
                "Fail With Noisy\n" +
                "- Backgrounds\n";

        assertEquals(expectedResult, result);
    }

    @Test
    @Ignore
    public void resolveImage_shouldResolveHandWrittenText() {
        File file = new File("src/test/resources/tessimage/hand_written_text.png");

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        String expectedResult = "05221859";

        assertEquals(expectedResult, result);
    }
}