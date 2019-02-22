package com.nixsolutions.pojo;

public enum Pages {
    ERROR_PAGE("showErrorPage"),
    ENGLISH_WORD_PAGE("englishWordPage" );

    private final String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
