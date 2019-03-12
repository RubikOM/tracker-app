package com.nixsolutions.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nixsolutions.config.HibernateConfig;
import com.nixsolutions.dao.DictionaryDao;
import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class DictionaryDaoImplTest {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Test
    @DatabaseSetup("/dataSet/TestDataSet.xml")
    public void getAllDictionaryElements() {
        User author = new User("user1", "user1Pass");
        DictionaryElement expectedValue1 = new DictionaryElement.Builder("testUser2_1", "тест2_1")
                .author(author).build();
        DictionaryElement expectedValue2 = new DictionaryElement.Builder("testUser2_2", "тест2_2")
                .author(author).build();
        List<DictionaryElement> expectedResult = new ArrayList<>();
        expectedResult.add(expectedValue1);
        expectedResult.add(expectedValue2);

        List<DictionaryElement> result = dictionaryDao.getAllDictionaryElements(author);

        assertEquals(expectedResult, result);
    }
}
