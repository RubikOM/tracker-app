package com.nixsolutions.pojo;

public enum Pages {
    ERROR_PAGE("showErrorPage"),
    USER_FIRST_PAGE("showUserPage"),
    ADMIN_FIRST_PAGE("showAdminPage"),
    ADMIN_CREATE_USER("performUserCreation"),
    ADMIN_UPDATE_USER("performUserUpdate"),
    REGISTRATION_PAGE("performUserRegistration"),
    AUTHENTICATION_PAGE("performUserAuthentication");

    private final String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
