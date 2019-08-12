package com.rubinskyi.dao;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DictionaryRepository extends CrudRepository<DictionaryElement, Long> {

    List<DictionaryElement> findAllByAuthor(User user);

    List<DictionaryElement> findFirst10ByAuthor(User user);

    DictionaryElement findByWordAndAuthor(String wordInEnglish, User user);

    List<DictionaryElement> findAllByAuthorAndCreationDate(User user, LocalDate date);
}
