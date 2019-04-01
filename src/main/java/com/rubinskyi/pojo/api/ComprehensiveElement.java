package com.rubinskyi.pojo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
public class ComprehensiveElement {
    private String dictionaryName;
    private String heading;
    private String complexHeading;
    private String transcription;
    private String translations;
    private String examples;
    private String partOfSpeech;
}
