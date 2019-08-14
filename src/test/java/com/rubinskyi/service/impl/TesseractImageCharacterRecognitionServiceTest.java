package com.rubinskyi.service.impl;

import com.rubinskyi.config.SpringTestConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
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

        String expectedResult = "The (quick) [brown] {fox} jumps!\n" +
                "Over the $43,456.78 <lazy> #90 dog\n" +
                "& duck/goose, as 12.5% of E-mail\n" +
                "from aspammer@website.com is spam.\n" +
                "Der ,schnelle” braune Fuchs springt\n" +
                "iiber den faulen Hund. Le renard brun\n" +
                "«rapide» saute par-dessus le chien\n" +
                "paresseux. La volpe marrone rapida\n" +
                "salta sopra il cane pigro. El zorro\n" +
                "marron ripido salta sobre el perro\n" +
                "perezoso. A raposa marrom ripida\n" +
                "salta sobre o cdo preguigoso.\n";

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
        assertEquals(expectedResult, result);
    }

    @Test
    public void resolveImage_realWorldImage_realBook() {
        File file = new File("src/test/resources/tessimage/javaBook.png");

        String expectedResult = "Clients neither know nor care about the class of the object they get back from the\n" +
                "factory; they care only that it is some subclass of EnumSet.\n" +
                "\n" +
                "A fifth advantage of static factories is that the class of the returned object\n" +
                "\n" +
                "need not exist when the class containing the method is written. Such flexible\n" +
                "static factory methods form the basis of service provider frameworks, like the Java\n" +
                "Database Connectivity API (JDBC). A service provider framework is a system in\n" +
                "which providers implement a service, and the system makes the implementations\n" +
                "available to clients, decoupling the clients from the implementations.\n" +
                "\n" +
                "There are three essential components in a service provider framework: a service\n" +
                "interface, which represents an implementation; a provider registration\n" +
                "\n" +
                "API, which providers use to register implementations; and a service access API,\n" +
                "which clients use to obtain instances of the service. The service access API may\n" +
                "allow clients to specify criteria for choosing an implementation. In the absence of\n" +
                "such criteria, the API returns an instance of a default implementation, or allows the\n" +
                "client to cycle through all available implementations. The service access API is the\n" +
                "flexible static factory that forms the basis of the service provider framework.\n" +
                "\n" +
                "An optional fourth component of a service provider framework is a service\n" +
                "provider interface, which describes a factory object that produce instances of the\n" +
                "service interface. In the absence of a service provider interface, implementations\n" +
                "‘must be instantiated reflectively (Item 65). In the case of JDBC, Connection\n" +
                "\n" +
                "plays the part of the service interface, DriverManager.registerDriver is the\n" +
                "provider registration API DriverManager.getConnection is the service access\n" +
                "API, and Driver is the service provider interface.\n" +
                "\n" +
                "There are many variants of the service provider framework pattern. For example,\n" +
                "th service access API can return a richer service interface to clients than the\n" +
                "\n" +
                "one furnished by providers. This is the Bridge pattern [Gamma95]. Dependency\n" +
                "injection frameworks (Item 5) can be viewed as powerful service providers. Since\n" +
                "Java 6, the platform includes a general-purpose service provider framework,\n" +
                "java.utilServiceLoader, so you needn’t, and generally shouldn’t, write your\n";

        String result = tesseractImageCharacterRecognitionService.resolveImage(file);
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