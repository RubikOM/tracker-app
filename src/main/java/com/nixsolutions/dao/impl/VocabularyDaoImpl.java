package com.nixsolutions.dao.impl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.dao.VocabularyDao;
import com.nixsolutions.entity.VocabularyElement;

@Repository
@Qualifier("hibernate")
public class VocabularyDaoImpl implements VocabularyDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(VocabularyDaoImpl.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public VocabularyDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VocabularyElement> getAllVocabularyElements() {
        Query query = sessionFactory.getCurrentSession().createQuery("from VocabularyElement");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VocabularyElement> getTodaysVocabularyElements() {
        Query query = sessionFactory.getCurrentSession().createQuery("from VocabularyElement where creationDate = :today");
        query.setParameter("today", LocalDate.now());
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public VocabularyElement findByWord(String englishName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from VocabularyElement where word = :param");
        query.setParameter("param", englishName);
        return (VocabularyElement) query.getSingleResult();
    }

    @Override
    @Transactional
    public void addVocabularyElement(VocabularyElement vocabularyElement) {
        sessionFactory.getCurrentSession().persist(vocabularyElement);
    }

    @Override
    @Transactional
    public void removeVocabularyElement(String word) {
        VocabularyElement element = findByWord(word);
        sessionFactory.getCurrentSession().remove(element);
    }
}
