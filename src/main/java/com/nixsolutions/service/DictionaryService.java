package com.nixsolutions.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nixsolutions.dao.DictionaryDao;
import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.entity.User;

@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;

    @Autowired
    public DictionaryService(@Qualifier("hibernate") DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    public List<DictionaryElement> getAllDictionaryElements(User user) {
        return dictionaryDao.getAllDictionaryElements(user);
    }

    public List<DictionaryElement> getLastDictionaryElements(User user) {
        return dictionaryDao.getLastDictionaryElements(user);
    }

    public List<DictionaryElement> getTodaysDictionaryElements(User user) {
        LocalDate today = LocalDate.now();
        return dictionaryDao.getTodaysDictionaryElements(user, today);
    }

    public DictionaryElement findByWord(String word, User user) {
        return dictionaryDao.findByWord(word, user);
    }

    public void createDictionaryElement(DictionaryElement dictionaryElement, User user) {
        dictionaryElement.setCreationDate(LocalDate.now());
        dictionaryElement.setAuthor(user);

        dictionaryDao.addDictionaryElement(dictionaryElement);
    }

    public void removeDictionaryElement(String wordToDelete, User user) {
        dictionaryDao.removeDictionaryElement(wordToDelete, user);
    }
}
