package com.nixsolutions.pojo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementFromApi {
    private Translation translation;
    private String[] seeAlso;

    public ElementFromApi() {
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    public String[] getSeeAlso() {
        return seeAlso;
    }

    public void setSeeAlso(String[] seeAlso) {
        this.seeAlso = seeAlso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementFromApi that = (ElementFromApi) o;
        return translation.equals(that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translation);
    }

    @Override
    public String toString() {
        return "ElementFromApi{" +
                "translation=" + translation +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Translation {
        private String heading;
        private String lingvoTranslations;
        private String socialTranslations;
        private String lingvoDictionaryName;
        private String type;
        private String source;

        public Translation() {
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getLingvoTranslations() {
            return lingvoTranslations;
        }

        public void setLingvoTranslations(String lingvoTranslations) {
            this.lingvoTranslations = lingvoTranslations;
        }

        public String getSocialTranslations() {
            return socialTranslations;
        }

        public void setSocialTranslations(String socialTranslations) {
            this.socialTranslations = socialTranslations;
        }

        public String getLingvoDictionaryName() {
            return lingvoDictionaryName;
        }

        public void setLingvoDictionaryName(String lingvoDictionaryName) {
            this.lingvoDictionaryName = lingvoDictionaryName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Translation that = (Translation) o;
            return heading.equals(that.heading) &&
                    lingvoTranslations.equals(that.lingvoTranslations);
        }

        @Override
        public int hashCode() {
            return Objects.hash(heading, lingvoTranslations);
        }

        @Override
        public String toString() {
            return "Translation{" +
                    "heading='" + heading + '\'' +
                    ", translations='" + lingvoTranslations + '\'' +
                    '}';
        }
    }
}
