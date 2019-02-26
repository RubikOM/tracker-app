package com.nixsolutions.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nixsolutions.dao.VocabularyDao;
import com.nixsolutions.entity.VocabularyElement;

@Service
public class VocabularyService {
    private final VocabularyDao vocabularyDao;

    @Autowired
    public VocabularyService(@Qualifier("hibernate") VocabularyDao vocabularyDao) {
        this.vocabularyDao = vocabularyDao;
    }

    // TODO get only last words
    public List<VocabularyElement> getVocabularyElementsWords() {
        return vocabularyDao.getAllVocabularyElements();
    }

    public List<VocabularyElement> getTodaysVocabularyElements() {
        return vocabularyDao.getTodaysVocabularyElements();
    }

    public void addVocabularyElement(VocabularyElement vocabularyElement) {
        // TODO some logic of setting date if vocabularyElement failed validation should be here
        vocabularyElement.setCreationDate(LocalDate.now());
        vocabularyDao.addVocabularyElement(vocabularyElement);
    }

    public void removeVocabularyElement(String wordToDelete) {
        vocabularyDao.removeVocabularyElement(wordToDelete);
    }
}
