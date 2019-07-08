package com.rubinskyi.service.api.impl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.DictionaryElementConsolidatorService;

@Service
@PropertySource("classpath:consolidation.properties")
public class SimpleDictionaryElementConsolidatorService implements DictionaryElementConsolidatorService {
    @Value("${delimiter}")
    private String TRANSLATIONS_DELIMITER;
    @Value("${defaultTranscription}")
    private String defaultTranscription;
    @Value("${totalTranslations}")
    private int TRANSLATIONS_AMOUNT;
    @Value("${totalExamples}")
    private int EXAMPLES_AMOUNT;

    @Override
    public DictionaryElement consolidateDictionaryElements(List<DictionaryElement> dictionaryElementList) {
        String word = dictionaryElementList.stream().map(DictionaryElement::getWord).filter(s -> !s.isEmpty()).findAny()
                .orElseThrow(() -> new IllegalStateException("Response should contain word"));
        String transcription = dictionaryElementList.stream().map(DictionaryElement::getTranscription).filter(s -> !s.isEmpty()).findAny()
                .orElse(defaultTranscription);
        List<String> examples = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExample));
        List<String> exampleTranslations = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExampleTranslation));
        List<String> translations = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getTranslation));
        LinkedHashSet<String> translationsUnique = new LinkedHashSet<>();
        for (String translation : translations) {
            String[] splitTranslations = translation.split(TRANSLATIONS_DELIMITER);
            Collections.addAll(translationsUnique, splitTranslations);
        }

        // TODO tests if examples and translations are empty
        String consolidatedExample = examples.stream().limit(EXAMPLES_AMOUNT).collect(Collectors.joining());
        String consolidatedExampleTranslation = exampleTranslations.stream().limit(EXAMPLES_AMOUNT).collect(Collectors.joining());
        String consolidatedTranslation = translationsUnique.stream().limit(TRANSLATIONS_AMOUNT)
                .collect(Collectors.joining(", ")).replaceAll(" +", " ");
        String consolidatedTranslationStyled = consolidatedTranslation.replace(";", ",");

        DictionaryElement element = new DictionaryElement.Builder(word, consolidatedTranslationStyled)
                .transcription(transcription.isEmpty() ? transcription : "[" + transcription + "]")
                .example(consolidatedExample)
                .exampleTranslation(consolidatedExampleTranslation)
                .build();

        return element;
    }

    private List<String> filterAndCollectToList(Stream<String> stream) {
        return stream.filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
