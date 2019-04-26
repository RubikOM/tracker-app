package com.rubinskyi.service.api;

import java.security.Principal;

import com.rubinskyi.entity.DictionaryElement;

public interface FormFillingService {

    DictionaryElement getDictionaryElementFromApi(String wordInEnglish, Principal principal);
}
