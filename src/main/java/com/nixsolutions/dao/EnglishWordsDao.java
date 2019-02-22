package com.nixsolutions.dao;

import java.util.List;

import com.nixsolutions.entity.EnglishWord;

public interface EnglishWordsDao {

    void createWord(EnglishWord englishWord);

    List<EnglishWord> getAllWords();
}
