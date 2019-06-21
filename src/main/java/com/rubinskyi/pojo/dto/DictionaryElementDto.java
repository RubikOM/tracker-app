package com.rubinskyi.pojo.dto;

import com.rubinskyi.entity.DictionaryElement;

import lombok.Getter;

@Getter
public class DictionaryElementDto {

    private final String word;
    private final String transcription;
    private final String translation;
    private final String example;
    private final String exampleTranslation;
    private final String errorMessage;

    public DictionaryElementDto(DictionaryElement dictionaryElement) {
        this.word = dictionaryElement.getWord();
        this.transcription = dictionaryElement.getTranscription();
        this.translation = dictionaryElement.getTranslation();
        this.example = dictionaryElement.getExample();
        this.exampleTranslation = dictionaryElement.getExampleTranslation();
        this.errorMessage = "";
    }

    public DictionaryElementDto(String word, String errorMessage) {
        this.word = word;
        this.transcription = "";
        this.translation = "";
        this.example = "";
        this.exampleTranslation = "";
        this.errorMessage = errorMessage;
    }
}
