package com.rubinskyi.dao.impl;

import com.rubinskyi.dao.DictionaryDao;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Qualifier("hibernate")
@Transactional
@Slf4j
public class DictionaryDaoImpl implements DictionaryDao {
    private final SessionFactory sessionFactory;

    private final static String SELECT_ALL_DICTIONARY_ELEMENTS = "from DictionaryElement where author.id = :author";
    private final static String SELECT_LAST_DICTIONARY_ELEMENTS = "from DictionaryElement where author.id = :author order by creationDate DESC, id DESC";
    private final static String SELECT_ALL_TODAYS_DICTIONARY_ELEMENTS = "from DictionaryElement where creationDate = :today and author.id = :author";
    private final static String SELECT_DICTIONARY_ELEMENT_BY_WORD = "from DictionaryElement where word = :word and author.id = :author";
    private final static Integer MAX_AMOUNT_OF_WORDS_DISPLAYED = 10;

    @Autowired
    public DictionaryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<DictionaryElement> getAllDictionaryElements(User author) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_ALL_DICTIONARY_ELEMENTS);
        query.setParameter("author", author.getId());
        return query.list();
    }

    @Override
    public List<DictionaryElement> getLastDictionaryElements(User author) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_LAST_DICTIONARY_ELEMENTS);
        query.setParameter("author", author.getId());
        query.setMaxResults(MAX_AMOUNT_OF_WORDS_DISPLAYED);
        return query.list();
    }

    @Override
    public List<DictionaryElement> getTodaysDictionaryElements(User author, LocalDate today) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_ALL_TODAYS_DICTIONARY_ELEMENTS);
        query.setParameter("today", today);
        query.setParameter("author", author.getId());
        return query.list();
    }

    @Override
    public DictionaryElement findByWord(String wordInEnglish, User author) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_DICTIONARY_ELEMENT_BY_WORD);
        query.setParameter("word", wordInEnglish);
        query.setParameter("author", author.getId());
        try {
            return (DictionaryElement) query.uniqueResult();
        } catch (HibernateException e) {
            log.error(wordInEnglish + " is not uniq in DB");
            List<DictionaryElement> result = query.list();
            return result.get(result.size() - 1);
        }
    }

    @Override
    public void addDictionaryElement(DictionaryElement dictionaryElement) {
        sessionFactory.getCurrentSession().persist(dictionaryElement);
    }

    @Override
    public void removeDictionaryElement(String word, User author) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        DictionaryElement element = findByWord(word, author);
        session.remove(element);
    }
}
