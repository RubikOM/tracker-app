package com.rubinskyi.pojo.lingvo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@ToString @EqualsAndHashCode
public class ComprehensiveElementLingvo {
    private String dictionaryName;
    private String heading;
    private String transcription;
    private String translations;
    private String examples;
}
