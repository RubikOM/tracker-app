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
public class EnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "word_in_english")
    private String wordInEnglish;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "translation")
    private String translation;

    @Column(name = "example")
    private String example;

    @Column(name = "example_translation")
    private String exampleTranslation;

    public EnglishWord() {
    }

    public EnglishWord(LocalDate creationDate, String wordInEnglish, String transcription, String translation,
                       String example, String exampleTranslation) {
        this.creationDate = creationDate;
        this.wordInEnglish = wordInEnglish;
        this.transcription = transcription;
        this.translation = translation;
        this.example = example;
        this.exampleTranslation = exampleTranslation;
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

    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
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

    public String getExampleTranslation() {
        return exampleTranslation;
    }

    public void setExampleTranslation(String exampleTranslation) {
        this.exampleTranslation = exampleTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnglishWord that = (EnglishWord) o;
        return wordInEnglish.equals(that.wordInEnglish) &&
                translation.equals(that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordInEnglish, translation);
    }

    @Override
    public String toString() {
        return "EnglishWord{" +
                "wordInEnglish='" + wordInEnglish + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translation='" + translation + '\'' +
                ", example='" + example + '\'' +
                ", exampleTranslation='" + exampleTranslation + '\'' +
                '}';
    }
}
