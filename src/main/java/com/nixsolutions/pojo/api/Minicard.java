package com.nixsolutions.pojo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Minicard {
    private Translation translation;

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
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

        public void setTranslations(String Translation) {
            this.translations = Translation;
        }

        public void setDictionary(String DictionaryName) {
            this.dictionary = DictionaryName;
        }
    }
}
