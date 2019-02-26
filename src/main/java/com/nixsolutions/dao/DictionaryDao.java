package com.nixsolutions.dao;

import java.util.List;

import com.nixsolutions.entity.DictionaryElement;

public interface DictionaryDao {

    List<DictionaryElement> getAllDictionaryElements();

    List<DictionaryElement> getLastDictionaryElements();

    List<DictionaryElement> getTodaysDictionaryElements();

    DictionaryElement findByWord(String wordInEnglish);

    void addDictionaryElement(DictionaryElement dictionaryElement);

    void removeDictionaryElement(String word);
}
