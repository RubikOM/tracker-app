package com.nixsolutions.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nixsolutions.dao.EnglishWordsDao;
import com.nixsolutions.entity.EnglishWord;

@Service
public class EnglishWordService {
    private final EnglishWordsDao englishWordsDao;

    @Autowired
    public EnglishWordService(@Qualifier("hibernate") EnglishWordsDao englishWordsDao) {
        this.englishWordsDao = englishWordsDao;
    }

    public void addWord(EnglishWord englishWord) {
        // TODO some logic of setting date if word failed validation should be here
        englishWord.setCreationDate(LocalDate.now());
        englishWordsDao.addWord(englishWord);
    }

    // TODO get only last words
    public List<EnglishWord> getAllWords() {
        return englishWordsDao.getAllWords();
    }
}
