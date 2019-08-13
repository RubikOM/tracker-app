package com.rubinskyi.service.api;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

import java.util.List;

public interface SuggestedTranslationService {

    List<DictionaryElement> getSuggestedElements(String englishText, User user);
}
