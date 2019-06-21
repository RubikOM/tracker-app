package com.rubinskyi.service.api.impl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.DictionaryElementConsolidatorService;

@Service
public class SimpleDictionaryElementConsolidatorService implements DictionaryElementConsolidatorService {
    private static final String TRANSLATIONS_DELIMITER = ";";
    private static final String DEFAULT_VALUE = "";
    // TODO take it from Properties file
    private static final int TRANSLATIONS_AMOUNT = 4;
    private static final int EXAMPLES_AMOUNT = 1;

    @Override
    public DictionaryElement consolidateDictionaryElements(List<DictionaryElement> dictionaryElementList) {
        // TODO here checks if List is empty

        String word = dictionaryElementList.stream().map(DictionaryElement::getWord).filter(s -> !s.isEmpty()).findAny().orElse(DEFAULT_VALUE);
        String transcription = dictionaryElementList.stream().map(DictionaryElement::getTranscription).filter(s -> !s.isEmpty()).findAny().orElse(DEFAULT_VALUE);
        List<String> examples = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExample));
        List<String> exampleTranslations = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExampleTranslation));
        List<String> translations = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getTranslation));
        LinkedHashSet<String> translationsUnique = new LinkedHashSet<>();
        for (String translation : translations) {
            String[] splitTranslations = translation.split(TRANSLATIONS_DELIMITER);
            Collections.addAll(translationsUnique, splitTranslations);
        }

        // TODO checks here and splittings
        String consolidatedExample = examples.stream().limit(EXAMPLES_AMOUNT).collect(Collectors.joining());
        String consolidatedExampleTranslation = exampleTranslations.stream().limit(EXAMPLES_AMOUNT).collect(Collectors.joining());
        String consolidatedTranslation = translationsUnique.stream().limit(TRANSLATIONS_AMOUNT)
                .collect(Collectors.joining(", ")).replaceAll(" +", " ");
        // TODO some check here
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
