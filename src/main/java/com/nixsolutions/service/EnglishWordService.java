package com.nixsolutions.service;

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

    public void createWord(EnglishWord englishWord){
        englishWordsDao.createWord(englishWord);
    }
}
