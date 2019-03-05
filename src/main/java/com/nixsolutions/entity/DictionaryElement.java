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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.nixsolutions.validation.dictionaryElement.WordUniq;

@Entity
@Table(name = "english_words")
public class DictionaryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "word_in_english")
    @NotBlank(message = "Word {shouldNotBeEmpty}")
    @Size(max = 40, message = "Word {size.mustBeLess}")
    @Pattern(regexp = "^[A-Za-z ,']*$", message = "{shouldBeEnglish}")
    @WordUniq
    private String word;

    @Column(name = "transcription")
    @Size(max = 40, message = "Transcription {size.mustBeLess}")
    private String transcription;

    @Column(name = "translation")
    @Size(max = 100, message = "Translation {size.mustBeLess}")
    @NotBlank(message = "Translation {shouldNotBeEmpty}")
//    @Pattern(regexp = "^[А-Яа-я ,]*$", message = "{shouldBeRussian}")
    private String translation;

    @Column(name = "example")
    @Size(max = 225, message = "Example {size.mustBeLess}")
    /*@TranslationShouldPresent(message = "You should declare example and its translation both, or not declare any." +
            " Hack 'fill the field with SPACE symbol if you really need it.'")*/
    private String example;

    @Column(name = "example_translation")
    @Size(max = 225, message = "Example translation {size.mustBeLess}")
    private String examplesTranslation;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public String getDictionaryElementAsString() {
        makeWordValid();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(";").append(transcription.equals("") ? "" : transcription + ";")
                .append(translation).append(";").append(example.equals("") ? "" : example + ";")
                .append(examplesTranslation.equals("") ? "" : examplesTranslation + ";").append("\n");
        return stringBuilder.toString();
    }

    private void makeWordValid() {
        if (!example.equals("") && examplesTranslation.equals("")) {
            examplesTranslation = " ";
        }
        if (!examplesTranslation.equals("") && example.equals("")) {
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

    // Builder is not necessary here, I can't afford validation to be in here (Spring MVC requires empty public constructor
    // and public setters to initiate an instance of this class, and validation takes its place by annotations on field -
    // not build() method in builder). But, this class is a good place to practice using creation patterns (e.g. Builder).
    // it has usage in unit test of this class and has default access(instead of public) level because of it.
    static class Builder {
        private final String word;
        private final String translation;

        private String transcription = "";
        private String example = "";
        private String examplesTranslation = "";

        Builder(String word, String translation) {
            this.word = word;
            this.translation = translation;
        }

        Builder transcription(String value) {
            transcription = value;
            return this;
        }

        Builder example(String value) {
            example = value;
            return this;
        }

        Builder exampleTranslation(String value) {
            examplesTranslation = value;
            return this;
        }

        DictionaryElement build() {
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
}
