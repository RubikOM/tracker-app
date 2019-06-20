package com.rubinskyi.pojo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class PartialElementLingvo {
    private Translation translation;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter @NoArgsConstructor
    @ToString @EqualsAndHashCode
    public class Translation {
        private String translations;
        private String dictionary;

        @JsonProperty("lingvoTranslations")
        public String getTranslations() {
            return translations;
        }

        @JsonProperty("lingvoDictionaryName")
        public String getDictionary() {
            return dictionary;
        }
    }
}
