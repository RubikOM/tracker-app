package com.rubinskyi.pojo;

public enum Pages {
    ERROR_PAGE("showErrorPage"),
    DICTIONARY_PAGE("dictionaryPage"),
    LOGIN_PAGE("login"),
    UPLOAD_FILE_PAGE("characterRecognitionPage"),
    EDIT_WORD_PAGE("editDictionaryElementPage");

    private final String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
