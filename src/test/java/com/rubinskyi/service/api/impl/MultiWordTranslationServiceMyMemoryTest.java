package com.rubinskyi.service.api.impl;

import com.rubinskyi.config.SpringTestConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class MultiWordTranslationServiceMyMemoryTest {

    @Autowired
    private MultiWordTranslationServiceMyMemory multiWordTranslation;

    @Test
    public void translateSentenceToRussian_shouldReturnCorrectResponse() {
        String uncroppedString = "PREREQUISITES In order to make the most of this, you will need to have a little bit of programming experience.";

        String expectedResult = "ПРЕДПОСЫЛКИ Для того, чтобы максимально использовать это, вам нужно иметь немного опыта программирования.";
        assertEquals(expectedResult, multiWordTranslation.translateSentenceToRussian(uncroppedString));
    }

    // API has amount of words a day to translate that's why @Ignore takes a place here
    @Test
    @Ignore
    public void translateSentenceToRussian_TranslatesTextBiggerThan500Characters() {
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

        String expectedResult = "ПРЕДПОСЫЛКИ Для того, чтобы максимально использовать это, вам нужно иметь немного опыта программирования. Все примеры в этой книге на языке программирования Python. Знакомство с Python или другими языками сценариев предлагается, но не обязательно. Вам также нужно знать основы математики. Эта книга написана на практике и основана на примерах: много примеров и много кода, поэтому, даже если ваши математические навыки не на высоте, не беспокойтесь! Примеры очень подробны и тщательно документированы, чтобы помочь вам следовать. И еще один вопрос? Тестировать.";
        assertEquals(expectedResult, multiWordTranslation.translateTextToRussian(initialString));
    }
}
