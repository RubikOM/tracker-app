package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.SpringTestConfigWithMockUser;
import com.rubinskyi.entity.Dictionary;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.Interest;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;
import com.rubinskyi.service.api.SuggestedTranslationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfigWithMockUser.class)
public class SuggestedTranslationServiceLingvoTest {
    @Autowired
    private User testUser;
    @Autowired
    private UserService userService;
    @Autowired
    private SuggestedTranslationService suggestedTranslationService;


    @Before
    public void prepareMockUser() {
        Set<Interest> interests = new HashSet<>();
        User userForTest = new User(1L, "mike", "mockPassword");
        interests.add(new Interest(userForTest, new Dictionary("LingvoComputer (En-Ru)"), 1));
        interests.add(new Interest(userForTest, new Dictionary("LingvoUniversal (En-Ru)"), 2));
        interests.add(new Interest(userForTest, new Dictionary("Learning (En-Ru)"), 3));
        userForTest.setInterests(interests);
        testUser = userForTest;

        Mockito.when(userService.findByLogin(Mockito.anyString())).thenReturn(testUser);
    }

    @Test
    public void getSuggestedElements() {
        String initialString = "PREREQUISITES\n" +
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
                "documented to help you follow along.\nAnd one more question? To test.";

        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(4, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_sentence() {
        String initialString = "In order to make the most of this, you will need to have a little bit of programming experience." +
                " All examples in this book are in the Python programming language. Familiarity with Python or other scripting languages is suggested, but not required.";

        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }

    @Test
    public void getSuggestedElements_largeText() {
        String initialString = "Clients neither know nor care about the class of the object they get back from the\n" +
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

        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(initialString, "mike");

        assertEquals(3, suggestedElements.size());
    }
}
