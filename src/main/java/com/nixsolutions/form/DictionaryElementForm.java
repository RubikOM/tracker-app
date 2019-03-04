package com.nixsolutions.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class DictionaryElementForm {

    @NotBlank(message = "Word {shouldNotBeEmpty}")
    @Pattern(regexp = "^[A-Za-z ,']*$", message = "{shouldBeEnglish}")
    private String word;

    private String transcription;

    @NotBlank(message = "Translation {shouldNotBeEmpty}")
    //    @Pattern(regexp = "^[А-Яа-я ,]*$", message = "{shouldBeRussian}")
    private String translation;

    private String example;

    private String examplesTranslation;

    public String getDictionaryElementAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(";").append(transcription.equals("") ? "" : transcription + ";")
                .append(translation).append(";").append(example.equals("") ? "" : example + ";")
                .append(examplesTranslation.equals("") ? "" : examplesTranslation + ";").append("\n");
        return stringBuilder.toString();
    }

    public DictionaryElementForm() {
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
}
