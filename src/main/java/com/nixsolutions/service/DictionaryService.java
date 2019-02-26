package com.nixsolutions.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.dao.DictionaryDao;
import com.nixsolutions.entity.DictionaryElement;

@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;

    @Autowired
    public DictionaryService(@Qualifier("hibernate") DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }


    @Transactional(readOnly = true)
    public List<DictionaryElement> getAllDictionaryElementsWords() {
        return dictionaryDao.getAllDictionaryElements();
    }

    @Transactional(readOnly = true)
    public List<DictionaryElement> getLastDictionaryElementsWords() {
        return dictionaryDao.getLastDictionaryElements();
    }

    @Transactional(readOnly = true)
    public List<DictionaryElement> getTodaysDictionaryElements() {
        return dictionaryDao.getTodaysDictionaryElements();
    }

    @Transactional
    public void addDictionaryElement(DictionaryElement dictionaryElement) {
        // TODO some logic of setting date if dictionaryElement failed validation should be here
        dictionaryElement.setCreationDate(LocalDate.now());
        dictionaryDao.addDictionaryElement(dictionaryElement);
    }

    @Transactional
    public void removeDictionaryElement(String wordToDelete) {
        dictionaryDao.removeDictionaryElement(wordToDelete);
    }
}
