package com.nixsolutions.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "english_words")
public class DictionaryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "word_in_english")
    @NotBlank(message = "Word {shouldNotBeEmpty}")
//    @Size(min = 2, message = "Word {shouldNotBeEmpty}")
    @Pattern(regexp = "^[A-Za-z ,']*$", message = "{shouldBeEnglish}")
    private String word;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "translation")
//    @Size(min = 2, message = "Word {shouldNotBeEmpty}")
    @NotBlank(message = "Translation {shouldNotBeEmpty}")
//    @Pattern(regexp = "^[А-Яа-я ,]*$", message = "{shouldBeRussian}")
    private String translation;

    @Column(name = "example")
    private String example;

    @Column(name = "example_translation")
    private String examplesTranslation;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public static class Builder {
        private final String word;
        private final String translation;

        private String transcription = "";
        private String example = "";
        private String examplesTranslation = "";

        public Builder(String word, String translation) {
            this.word = word;
            this.translation = translation;
        }

        public Builder transcription(String value) {
            transcription = value;
            return this;
        }

        public Builder example(String value) {
            example = value;
            return this;
        }

        public Builder exampleTranslation(String value) {
            examplesTranslation = value;
            return this;
        }

        public DictionaryElement build() {
            return new DictionaryElement(this);
        }
    }

    private DictionaryElement(Builder builder) {
        word = builder.word;
        transcription = builder.transcription;
        translation = builder.translation;
        example = builder.example;
        examplesTranslation = builder.examplesTranslation;
    }

    public String getDictionaryElementAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(";").append(transcription.equals("") ? "" : transcription + ";")
                .append(translation).append(";").append(example.equals("") ? "" : example + ";")
                .append(examplesTranslation.equals("") ? "" : examplesTranslation + ";").append("\n");
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

    public String getTranscription() {
        return transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public String getExample() {
        return example;
    }

    public String getExamplesTranslation() {
        return examplesTranslation;
    }

    public DictionaryElement() {
        // For Spring
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setExample(String example) {
        this.example = example;
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
