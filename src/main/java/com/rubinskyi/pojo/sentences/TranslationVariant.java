package com.rubinskyi.pojo.sentences;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@ToString @EqualsAndHashCode
public class TranslationVariant {
    @JsonProperty("segment")
    String similarText;
    @JsonProperty("translation")
    String similarTextTranslation;
    @JsonProperty("quality")
    Double translationQuality;
    @JsonProperty("reference")
    String translationAuthor;
    @JsonProperty("match")
    Double matchesInitialText;
}
