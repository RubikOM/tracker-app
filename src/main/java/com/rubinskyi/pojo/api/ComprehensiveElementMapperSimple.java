package com.rubinskyi.pojo.api;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.rubinskyi.entity.DictionaryElement;

@Component
public class ComprehensiveElementMapperSimple implements ComprehensiveElementMapper {

    // TODO rewrite this code
    public DictionaryElement comprehensiveElementToDictionaryElement(ComprehensiveElementLingvo comprehensiveElementLingvo) {
        DictionaryElement dictionaryElement = new DictionaryElement();
        mapConcreteWord(comprehensiveElementLingvo, dictionaryElement);
        mapExampleAndExampleTranslation(comprehensiveElementLingvo, dictionaryElement);
        mapTranscription(comprehensiveElementLingvo, dictionaryElement);
        mapTranslation(comprehensiveElementLingvo, dictionaryElement);
        return dictionaryElement;
    }

    private void mapConcreteWord(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                 @NotNull DictionaryElement result) {
        if (!comprehensiveElement.getHeading().isEmpty()) {
            result.setWord(comprehensiveElement.getHeading());
        }
    }

    private void mapExampleAndExampleTranslation(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                                 @NotNull DictionaryElement result) {
        if (!comprehensiveElement.getExamples().isEmpty()) {
            String examplesAsString = comprehensiveElement.getExamples();
            String[] examples = examplesAsString.split("—|\\r?\\n");
            if (examples.length >= 2) {
                String example = examples[0].trim();
                String exampleTranslation = examples[1].trim();

                result.setExample(example);
                result.setExampleTranslation(exampleTranslation);
            }
        }
    }

    private void mapTranscription(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                  @NotNull DictionaryElement result) {
        String transcription = comprehensiveElement.getTranscription();
        if (!transcription.isEmpty()) {
            result.setTranscription(transcription);
        }
    }

    private void mapTranslation(@NotNull ComprehensiveElementLingvo comprehensiveElement,
                                @NotNull DictionaryElement result) {
        String translation = comprehensiveElement.getTranslations();
        if (!translation.isEmpty()) {
            result.setTranslation(translation);
        }
    }
}
