package com.nixsolutions.pojo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Minicard {
    private Translation translation;

    // Getter Methods

    public Translation getTranslation() {
        return translation;
    }

    // Setter Methods

    public void setTranslation(Translation TranslationObject) {
        this.translation = TranslationObject;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Translation {
        private String translations;
        private String dictionary;

        // Getter Methods

        @JsonProperty("lingvoTranslations")
        public String getTranslations() {
            return translations;
        }

        @JsonProperty("lingvoDictionaryName")
        public String getDictionary() {
            return dictionary;
        }

        // Setter Methods

        public void setTranslations(String Translation) {
            this.translations = Translation;
        }

        public void setDictionary(String DictionaryName) {
            this.dictionary = DictionaryName;
        }
    }


}
