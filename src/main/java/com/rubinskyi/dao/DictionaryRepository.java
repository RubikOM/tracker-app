package com.rubinskyi.dao;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DictionaryRepository extends PagingAndSortingRepository<DictionaryElement, Long> {

    List<DictionaryElement> findAllByAuthor(User user);

    List<DictionaryElement> findAllByAuthor(User user, Pageable pageable);

    DictionaryElement findByWordAndAuthor(String wordInEnglish, User user);

    List<DictionaryElement> findAllByAuthorAndCreationDate(User user, LocalDate date);
}
