package com.rubinskyi.service.api;

import java.util.Map;

public interface FileTranslationService {

    Map<String, Object> getTranslations(String text, String username);
}
