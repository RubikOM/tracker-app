package com.rubinskyi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;

@Service
public class SimpleDictionaryElementConsolidatorService implements DictionaryElementConsolidatorService {
    // TODO rewrite to optionals??

    @Override
    public DictionaryElement consolidateDictionaryElements(List<DictionaryElement> dictionaryElementList) {
        DictionaryElement result = new DictionaryElement();

        for (DictionaryElement dictionaryElement : dictionaryElementList) {
            if (result.getWord() == null || result.getWord().isEmpty()) {
                result.setWord(dictionaryElement.getWord());
            }

            if (result.getTranscription() == null || result.getTranscription().isEmpty()) {
                result.setTranscription(dictionaryElement.getTranscription());
            }
            if (result.getTranslation() == null || result.getTranslation().isEmpty()) {
                result.setTranslation(dictionaryElement.getTranslation());
            }
            if (result.getExample() == null || result.getExample().isEmpty()) {
                result.setExample(dictionaryElement.getExample());
                result.setExampleTranslation(dictionaryElement.getExampleTranslation());
            }
            if ((result.getTranscription() != null && !result.getTranslation().isEmpty())
                    && (result.getTranscription() != null && !result.getTranscription().isEmpty())
                    && (result.getExample() != null && !result.getExample().isEmpty())) {
                return result;
            }
        }

        return result;
    }
}
