package com.rubinskyi.entity;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryElementTest {

    @Test
    public void getDictionaryElementAsString() {
        DictionaryElement dictionaryElement = new DictionaryElement.Builder("word", "перевод").build();

        Assert.assertEquals("word;перевод;\n", dictionaryElement.importer().getDictionaryElementAsString());
    }

    @Test
    public void getDictionaryElementAsString_Transcription() {
        DictionaryElement dictionaryElement = new DictionaryElement.Builder("word", "перевод")
                .transcription("[wɜrd]").build();

        Assert.assertEquals("word;[wɜrd];перевод;\n", dictionaryElement.importer().getDictionaryElementAsString());
    }

    @Test
    public void getDictionaryElementAsString_AllFields() {
        DictionaryElement dictionaryElement = new DictionaryElement.Builder("word", "перевод")
                .transcription("[wɜrd]").example("word word word").exampleTranslation("перевод перевод перевод").build();

        Assert.assertEquals("word;[wɜrd];перевод;word word word;перевод перевод перевод;\n",
                dictionaryElement.importer().getDictionaryElementAsString());
    }

    @Test
    public void getDictionaryElementAsString_noExample() {
        DictionaryElement dictionaryElement = new DictionaryElement.Builder("word", "перевод")
                .transcription("[wɜrd]").exampleTranslation("перевод перевод перевод").build();

        Assert.assertEquals("word;[wɜrd];перевод; ;перевод перевод перевод;\n",
                dictionaryElement.importer().getDictionaryElementAsString());
    }

    @Test
    public void getDictionaryElementAsString_noExamplesTranslation() {
        DictionaryElement dictionaryElement = new DictionaryElement.Builder("word", "перевод")
                .transcription("[wɜrd]").example("word word word").build();

        Assert.assertEquals("word;[wɜrd];перевод;word word word; ;\n",
                dictionaryElement.importer().getDictionaryElementAsString());
    }

}
