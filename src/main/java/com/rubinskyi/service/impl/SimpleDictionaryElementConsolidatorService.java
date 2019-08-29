package com.rubinskyi.service.impl;

import com.rubinskyi.bean.properties.ApiProperties;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.DictionaryElementConsolidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rubinskyi.pojo.constant.StringConstant.COMMA;
import static com.rubinskyi.pojo.constant.StringConstant.SEMICOLON;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Service
@RequiredArgsConstructor
public class SimpleDictionaryElementConsolidatorService implements DictionaryElementConsolidatorService {
    private final ApiProperties apiProperties;

    @Override
    public DictionaryElement consolidateDictionaryElements(List<DictionaryElement> dictionaryElementList) {
        String word = dictionaryElementList.stream().map(DictionaryElement::getWord).filter(s -> !s.isEmpty()).findAny()
                .orElseThrow(() -> new IllegalStateException("Response should contain word"));
        String transcription = dictionaryElementList.stream().map(DictionaryElement::getTranscription).filter(s -> !s.isEmpty()).findAny()
                .orElse(EMPTY);
        List<String> examples = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExample));
        List<String> exampleTranslations = filterAndCollectToList(dictionaryElementList.stream().map(DictionaryElement::getExampleTranslation));
        LinkedHashSet<String> translations = getUniqueTranslations(filterAndCollectToList(dictionaryElementList
                .stream().map(DictionaryElement::getTranslation)));

        String consolidatedExample = examples.stream().limit(apiProperties.getExamplesAmount()).collect(Collectors.joining());
        String consolidatedExampleTranslation = exampleTranslations.stream().limit(apiProperties.getExamplesAmount()).collect(Collectors.joining());
        String consolidatedTranslation = translations.stream().limit(apiProperties.getTranslationsAmount())
                .collect(Collectors.joining(", ")).replaceAll(" +", SPACE);
        String consolidatedTranslationStyled = consolidatedTranslation
                .replace(SEMICOLON, COMMA).replace(" ||", COMMA)
                .replace("!", EMPTY).replace("?", EMPTY);

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

    private LinkedHashSet<String> getUniqueTranslations(List<String> translations) {
        LinkedHashSet<String> translationsUnique = new LinkedHashSet<>();
        for (String translation : translations) {
            String[] splitTranslations = translation.split(SEMICOLON);
            Collections.addAll(translationsUnique, splitTranslations);
        }
        return translationsUnique;
    }
}
