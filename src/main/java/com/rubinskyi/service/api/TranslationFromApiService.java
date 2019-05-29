package com.rubinskyi.service.api;

import java.security.Principal;

import com.rubinskyi.entity.DictionaryElement;

public interface TranslationFromApiService {

    DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal);
}
