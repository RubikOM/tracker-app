package com.rubinskyi.service.outerApi;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

public interface ComprehensiveTranslationService {

    DictionaryElement getDictionaryElementFromApi(String wordInEnglish, User user);
}
