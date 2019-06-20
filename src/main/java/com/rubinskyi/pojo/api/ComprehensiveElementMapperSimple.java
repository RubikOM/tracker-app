package com.rubinskyi.pojo.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rubinskyi.entity.DictionaryElement;

@Component
public class ComprehensiveElementMapperSimple implements ComprehensiveElementMapper {

    // TODO get optional in method here
    public DictionaryElement comprehensiveElementToDictionaryElement(ComprehensiveElementLingvo comprehensiveElementLingvo) {
        // TODO this has to be rewritten
        if (comprehensiveElementLingvo == null) return new DictionaryElement();
        Optional<ComprehensiveElementLingvo> elementLingvo = Optional.of(comprehensiveElementLingvo);

        Optional<String> word = elementLingvo.map(ComprehensiveElementLingvo::getHeading);
        Optional<String> translations = elementLingvo.map(ComprehensiveElementLingvo::getTranslations);
        Optional<String> transcription = elementLingvo.map(ComprehensiveElementLingvo::getTranscription);
        Optional<String> mixedExamples = elementLingvo.map(ComprehensiveElementLingvo::getExamples);
        Map<String, String> examplesMap = getExampleAndExampleTranslation(mixedExamples);

        DictionaryElement result = new DictionaryElement.Builder(word.orElse(""), translations.orElse(""))
                .transcription(transcription.orElse(""))
                .example(examplesMap.getOrDefault("example", ""))
                .exampleTranslation(examplesMap.getOrDefault("example", ""))
                .build();

        return result;
    }

    // TODO fix Optional<String> idea error
    private Map<String, String> getExampleAndExampleTranslation(Optional<String> mixedExamples) {
        Map<String, String> result = new HashMap<>();
        if (!mixedExamples.isPresent()) return result;
        String examplesString = mixedExamples.orElse("");

        if (!examplesString.isEmpty()) {
            String[] examplesArray = examplesString.split("—|\\r?\\n");
            if (examplesArray.length >= 2) {
                result.put("example", examplesArray[0].trim());
                result.put("exampleTranslation", examplesArray[1].trim());
            }
        }
        return result;
    }
}
