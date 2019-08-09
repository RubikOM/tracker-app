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
public class SentenceElementMyMemory {
    RussianSentenceResponse responseData;
    Boolean quotaFinished;
    String responseDetails;
    Integer exception_code;

    // TODO add here list of suggested translations

}
