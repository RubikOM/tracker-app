package com.nixsolutions.pojo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Minicard {
    private Translation translation;

    @JsonProperty("Translation")
    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    // TODO make this class private
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Translation {
        private String translations;
        private String dictionary;

        @JsonProperty("Translation")
        public String getTranslations() {
            return translations;
        }

        @JsonProperty("DictionaryName")
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
