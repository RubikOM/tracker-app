package com.rubinskyi.dao;

import java.time.LocalDate;
import java.util.List;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

public interface DictionaryDao {

    List<DictionaryElement> getAllDictionaryElements(User user);

    List<DictionaryElement> getLastDictionaryElements(User user);

    List<DictionaryElement> getTodaysDictionaryElements(User user, LocalDate today);

    DictionaryElement findByWord(String wordInEnglish, User user);

    void addDictionaryElement(DictionaryElement dictionaryElement);

    void removeDictionaryElement(String word, User user);
}
