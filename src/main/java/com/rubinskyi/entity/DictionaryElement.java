package com.rubinskyi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.NotBlank;

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
import java.time.LocalDate;
import java.util.Objects;

import static com.rubinskyi.pojo.constant.StringConstant.LINE_BREAK;
import static com.rubinskyi.pojo.constant.StringConstant.SEMICOLON;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Entity
@Table(name = "DICTIONARY_ELEMENTS")
@Data
@NoArgsConstructor
@BatchSize(size = 50)
public class DictionaryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
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
    @EqualsAndHashCode.Exclude
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @EqualsAndHashCode.Exclude
    private User author;

    // Builder is not necessary here, but I want it to be here c:
    public static class Builder {
        private final String word;
        private final String translation;

        private String transcription = EMPTY;
        private String example = EMPTY;
        private String exampleTranslation = EMPTY;
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

    public Importer importer() {
        return new Importer();
    }

    public class Importer {
        public String getDictionaryElementAsString() {
            if (translation == null) {
                return EMPTY;
            } else {
                makeWordValidForFile();

                return word + SEMICOLON +
                        (Objects.equals(transcription, EMPTY) ? EMPTY : transcription.concat(SEMICOLON)) +
                        translation + SEMICOLON +
                        (Objects.equals(example, EMPTY) ? EMPTY : example.concat(SEMICOLON)) +
                        (Objects.equals(exampleTranslation, EMPTY) ? EMPTY : exampleTranslation.concat(SEMICOLON)) +
                        LINE_BREAK;
            }
        }

        private void makeWordValidForFile() {
            if (!Objects.equals(example, EMPTY) && Objects.equals(exampleTranslation, EMPTY)) {
                exampleTranslation = SPACE;
            }
            if (!Objects.equals(exampleTranslation, EMPTY) && Objects.equals(example, EMPTY)) {
                example = SPACE;
            }
        }
    }
}
