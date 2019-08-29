package com.rubinskyi.service.outerApi;

import java.util.Map;

public interface FileTranslationService {

    Map<String, Object> getTranslations(String text, String username);
}
