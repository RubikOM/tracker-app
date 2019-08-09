package com.rubinskyi.pojo.sentences;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@ToString @EqualsAndHashCode
public
class RussianSentenceResponse {
    String translatedText;
    Double match;
}
