package com.rubinskyi.service.impl;

import com.rubinskyi.dao.DictionaryElementRepository;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.DictionaryElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictionaryElementElementServiceImpl implements DictionaryElementService {
    private final DictionaryElementRepository dictionaryElementRepository;

    public List<DictionaryElement> getAllDictionaryElements(User user) {
        return dictionaryElementRepository.findAllByAuthor(user);
    }

    public List<DictionaryElement> getLastDictionaryElements(User user) {
        Pageable pageable = PageRequest.of(0, 10);
        return dictionaryElementRepository.findAllByAuthor(user, pageable);
    }

    public List<DictionaryElement> getTodaysDictionaryElements(User user) {
        LocalDate today = LocalDate.now();
        return dictionaryElementRepository.findAllByAuthorAndCreationDate(user, today);
    }

    public DictionaryElement findByWord(String word, User user) {
        return dictionaryElementRepository.findByWordAndAuthor(word, user);
    }

    public void createDictionaryElement(DictionaryElement dictionaryElement, User user) {
        dictionaryElement.setCreationDate(LocalDate.now());
        dictionaryElement.setAuthor(user);

        dictionaryElementRepository.save(dictionaryElement);
    }

    public void removeDictionaryElement(String wordToDelete, User user) {
        DictionaryElement byWord = findByWord(wordToDelete, user);

        dictionaryElementRepository.delete(byWord);
    }
}
