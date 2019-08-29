package com.rubinskyi.service.outerApi;

import java.security.Principal;

import com.rubinskyi.entity.DictionaryElement;

public interface TranslationFromApiService {

    DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal);
}
