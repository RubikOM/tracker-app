package com.nixsolutions.dao;

import java.util.List;

import com.nixsolutions.entity.VocabularyElement;

public interface VocabularyDao {

    List<VocabularyElement> getAllVocabularyElements();

    List<VocabularyElement> getTodaysVocabularyElements();

    VocabularyElement findByWord(String wordInEnglish);

    void addVocabularyElement(VocabularyElement vocabularyElement);

    void removeVocabularyElement(String word);
}
