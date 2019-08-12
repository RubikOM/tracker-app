package com.rubinskyi.service.impl;

import com.rubinskyi.dao.DictionaryRepository;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<DictionaryElement> getAllDictionaryElements(User user) {
        return dictionaryRepository.findAllByAuthor(user);
    }

    public List<DictionaryElement> getLastDictionaryElements(User user) {
        return dictionaryRepository.findFirst10ByAuthor(user);
    }

    public List<DictionaryElement> getTodaysDictionaryElements(User user) {
        LocalDate today = LocalDate.now();
        return dictionaryRepository.findAllByAuthorAndCreationDate(user, today);
    }

    public DictionaryElement findByWord(String word, User user) {
        return dictionaryRepository.findByWordAndAuthor(word, user);
    }

    public void createDictionaryElement(DictionaryElement dictionaryElement, User user) {
        dictionaryElement.setCreationDate(LocalDate.now());
        dictionaryElement.setAuthor(user);

        dictionaryRepository.save(dictionaryElement);
    }

    public void removeDictionaryElement(String wordToDelete, User user) {
        DictionaryElement byWord = findByWord(wordToDelete, user);

        dictionaryRepository.delete(byWord);
    }
}
