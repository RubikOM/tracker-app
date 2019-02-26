package com.nixsolutions.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "english_words")
public class DictionaryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "word_in_english")
    private String word;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "translation")
    private String translation;

    @Column(name = "example")
    private String example;

    @Column(name = "example_translation")
    private String examplesTranslation;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public DictionaryElement() {
    }

    public DictionaryElement(String word, String transcription, String translation,
                             String example, String examplesTranslation) {
        this.word = word;
        this.transcription = transcription;
        this.translation = translation;
        this.example = example;
        this.examplesTranslation = examplesTranslation;
    }

    //TODO test this method
    public String getVocabularyElementAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(";").append(transcription == null ? "" : transcription + ";")
                .append(translation).append(";").append(example == null? "" : example + ";")
                .append(examplesTranslation == null ? "" : examplesTranslation + ";").append("\n");
        return stringBuilder.toString();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExamplesTranslation() {
        return examplesTranslation;
    }

    public void setExamplesTranslation(String examplesTranslation) {
        this.examplesTranslation = examplesTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryElement that = (DictionaryElement) o;
        return word.equals(that.word) &&
                translation.equals(that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, translation);
    }

    @Override
    public String toString() {
        return "DictionaryElement{" +
                "word='" + word + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translation='" + translation + '\'' +
                ", example='" + example + '\'' +
                ", examplesTranslation='" + examplesTranslation + '\'' +
                '}';
    }
}
