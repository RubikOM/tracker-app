package com.rubinskyi.service;

import java.util.List;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

public interface DictionaryElementService {
    List<DictionaryElement> getAllDictionaryElements(User user);

    List<DictionaryElement> getLastDictionaryElements(User user);

    List<DictionaryElement> getTodaysDictionaryElements(User user);

    DictionaryElement findByWord(String word, User user);

    void createDictionaryElement(DictionaryElement dictionaryElement, User user);

    void removeDictionaryElement(String wordToDelete, User user);
}
