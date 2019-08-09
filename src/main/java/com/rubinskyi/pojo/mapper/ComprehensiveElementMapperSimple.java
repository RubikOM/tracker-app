package com.rubinskyi.pojo.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.lingvo.ComprehensiveElementLingvo;

@Component
public class ComprehensiveElementMapperSimple implements ComprehensiveElementMapper {

    public DictionaryElement comprehensiveElementToDictionaryElement(ComprehensiveElementLingvo comprehensiveElementLingvo) {
        if (comprehensiveElementLingvo == null) return new DictionaryElement();
        Optional<ComprehensiveElementLingvo> elementLingvo = Optional.of(comprehensiveElementLingvo);

        Optional<String> word = elementLingvo.map(ComprehensiveElementLingvo::getHeading);
        Optional<String> translations = elementLingvo.map(ComprehensiveElementLingvo::getTranslations);
        Optional<String> transcription = elementLingvo.map(ComprehensiveElementLingvo::getTranscription);
        Optional<String> mixedExamples = elementLingvo.map(ComprehensiveElementLingvo::getExamples);
        Map<String, String> examplesMap = getExampleAndExampleTranslation(mixedExamples.orElse(""));

        DictionaryElement result = new DictionaryElement.Builder(word.orElse(""), translations.orElse(""))
                .transcription(transcription.orElse(""))
                .example(examplesMap.getOrDefault("example", ""))
                .exampleTranslation(examplesMap.getOrDefault("exampleTranslation", ""))
                .build();

        return result;
    }

    private Map<String, String> getExampleAndExampleTranslation(String mixedExamples) {
        Map<String, String> result = new HashMap<>();
        if (mixedExamples.isEmpty()) return result;

        String fixedExamples = mixedExamples.replace(" / ", " | ");
        String[] examplesArray = fixedExamples.split("—|\\r?\\n|\\/");
        if (examplesArray.length >= 2) {
            result.put("example", examplesArray[0].trim());
            result.put("exampleTranslation", examplesArray[1].trim());
        }
        return result;
    }
}
