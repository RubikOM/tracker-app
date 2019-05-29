package com.rubinskyi.service.api;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

public interface ComprehensiveTranslationService {

    DictionaryElement obtainDataFromApi(String wordInEnglish, User user);
}