package com.rubinskyi.service.api;

import com.rubinskyi.entity.DictionaryElement;

import java.util.List;

public interface SuggestedTranslationService {

    List<DictionaryElement> getSuggestedElements(String englishText, String userName);
}
