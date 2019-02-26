package com.nixsolutions.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.dao.EnglishWordsDao;
import com.nixsolutions.entity.EnglishWord;

@Repository
@Qualifier("hibernate")
public class EnglishWordsDaoImpl implements EnglishWordsDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(EnglishWordsDaoImpl.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public EnglishWordsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void addWord(EnglishWord englishWord) {
        sessionFactory.getCurrentSession().persist(englishWord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnglishWord> getAllWords() {
        // TODO rebuild this shit from criteria to smth normal
        List<EnglishWord> list = sessionFactory.getCurrentSession().createCriteria(EnglishWord.class).list();
        return list;
    }
}
