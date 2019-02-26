package com.nixsolutions.dao.impl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.dao.DictionaryDao;
import com.nixsolutions.entity.DictionaryElement;

@Repository
@Qualifier("hibernate")
public class DictionaryDaoImpl implements DictionaryDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(DictionaryDaoImpl.class);

    private final SessionFactory sessionFactory;
    private final static String SELECT_ALL_DICTIONARY_ELEMENTS = "from DictionaryElement";
    private final static String SELECT_LAST_DICTIONARY_ELEMENTS = "from DictionaryElement order by creationDate DESC, id DESC";
    private final static String SELECT_ALL_TODAYS_DICTIONARY_ELEMENTS = "from DictionaryElement where creationDate = :today";
    private final static String SELECT_DICTIONARY_ELEMENT_BY_WORD = "from DictionaryElement where word = :param";
    private final static Integer MAX_AMOUNT_OF_WORDS_DISPLAYED = 10;

    @Autowired
    public DictionaryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<DictionaryElement> getAllDictionaryElements() {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_ALL_DICTIONARY_ELEMENTS);
        return query.list();
    }

    // TODO code duplication between this 2 methods
    @Override
    public List<DictionaryElement> getLastDictionaryElements() {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_LAST_DICTIONARY_ELEMENTS);
        query.setMaxResults(MAX_AMOUNT_OF_WORDS_DISPLAYED);
        return query.list();
    }

    @Override
    public List<DictionaryElement> getTodaysDictionaryElements() {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_ALL_TODAYS_DICTIONARY_ELEMENTS);
        query.setParameter("today", LocalDate.now());
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryElement findByWord(String englishName) {
        Query query = sessionFactory.getCurrentSession().createQuery(SELECT_DICTIONARY_ELEMENT_BY_WORD);
        query.setParameter("param", englishName);
        return (DictionaryElement) query.uniqueResult();
    }

    @Override
    public void addDictionaryElement(DictionaryElement dictionaryElement) {
        sessionFactory.getCurrentSession().persist(dictionaryElement);
    }

    @Override
    public void removeDictionaryElement(String word) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        DictionaryElement element = findByWord(word);
        session.remove(element);
    }
}
