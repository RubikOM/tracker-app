package com.rubinskyi.service.outerApi;

import com.rubinskyi.entity.DictionaryElement;

import java.util.List;

public interface SuggestedTranslationService {

    List<DictionaryElement> getSuggestedElements(String englishText, String userName);
}
