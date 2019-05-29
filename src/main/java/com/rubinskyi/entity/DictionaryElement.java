package com.rubinskyi.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "DICTIONARY_ELEMENTS")
public class DictionaryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word_in_english")
    @NotBlank(message = "Word {shouldNotBeEmpty}")
    @Size(max = 40, message = "Word {size.mustBeLess}")
    @Pattern(regexp = "^[A-Za-z ,']*$", message = "{shouldBeEnglish}")
    private String word;

    @Column(name = "transcription")
    @Size(max = 40, message = "Transcription {size.mustBeLess}")
    private String transcription;

    @Column(name = "translation")
    @Size(max = 100, message = "Translation {size.mustBeLess}")
    @NotBlank(message = "Translation {shouldNotBeEmpty}")
    private String translation;

    @Column(name = "example")
    @Size(max = 225, message = "Example {size.mustBeLess}")
    private String example;

    @Column(name = "example_translation")
    @Size(max = 225, message = "Example translation {size.mustBeLess}")
    private String exampleTranslation;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User author;

    public void concatenateTranslations(String additionalTranslation) {
        translation = translation.concat(additionalTranslation);
    }

    public String getDictionaryElementAsString() {
        if (translation == null) return "";
        makeWordValidForFile();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(";").append(transcription.equals("") ? "" : transcription + ";")
                .append(translation).append(";").append(example.equals("") ? "" : example + ";")
                .append(exampleTranslation.equals("") ? "" : exampleTranslation + ";").append("\n");
        return stringBuilder.toString();
    }

    private void makeWordValidForFile() {
        if (!example.equals("") && exampleTranslation.equals("")) {
            exampleTranslation = " ";
        }
        if (!exampleTranslation.equals("") && example.equals("")) {
            example = " ";
        }
    }

    public DictionaryElement() {
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getExampleTranslation() {
        return exampleTranslation;
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

    public void setExampleTranslation(String exampleTranslation) {
        this.exampleTranslation = exampleTranslation;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
                ", exampleTranslation='" + exampleTranslation + '\'' +
                ", created=" + creationDate +
                ", author=" + author +
                '}';
    }

    // Builder is not necessary here, I can't afford validation to be in builder (Spring MVC requires empty public constructor
    // and public setters to initiate an instance of class, and validation takes its place by annotations on field -
    // not build() method in builder). But, this class is a good place to practice using creation patterns (e.g. Builder)
    // because of variations in optional fields
    public static class Builder {
        private final String word;
        private final String translation;

        private String transcription = "";
        private String example = "";
        private String exampleTranslation = "";
        private User author = null;

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
            exampleTranslation = value;
            return this;
        }

        public Builder author(User value) {
            author = value;
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
        exampleTranslation = builder.exampleTranslation;
        author = builder.author;
    }
}
