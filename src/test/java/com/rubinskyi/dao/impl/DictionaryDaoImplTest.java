package com.rubinskyi.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.rubinskyi.config.HibernateConfig;
import com.rubinskyi.dao.DictionaryDao;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class DictionaryDaoImplTest {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void getAllDictionaryElements() {
        User author = new User(2L, "user2", "user2Pass");
        LocalDate creationDate = LocalDate.parse("2019-01-02");

        DictionaryElement expectedValue1 = new DictionaryElement();
        expectedValue1.setWord("testUser2_1");
        expectedValue1.setTranslation("тест2_1");
        expectedValue1.setAuthor(author);
        expectedValue1.setCreationDate(creationDate);

        DictionaryElement expectedValue2 = new DictionaryElement();
        expectedValue2.setWord("testUser2_2");
        expectedValue2.setTranslation("тест2_2");
        expectedValue2.setAuthor(author);
        expectedValue2.setCreationDate(creationDate);

        List<DictionaryElement> expectedResult = new ArrayList<>();
        expectedResult.add(expectedValue1);
        expectedResult.add(expectedValue2);

        List<DictionaryElement> result = dictionaryDao.getAllDictionaryElements(author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void getAllDictionaryElements_emptyList() {
        User author = new User(100500L, "NoSuchUserHere", "NoSuchUserHere");

        List expectedResult = new ArrayList();
        List<DictionaryElement> result = dictionaryDao.getAllDictionaryElements(author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByWord() {
        User author = new User(2L, "user2", "user2Pass");
        LocalDate creationDate = LocalDate.parse("2019-01-02");

        DictionaryElement expectedResult = new DictionaryElement();
        expectedResult.setWord("testUser2_1");
        expectedResult.setTranslation("тест2_1");
        expectedResult.setAuthor(author);
        expectedResult.setCreationDate(creationDate);

        DictionaryElement result = dictionaryDao.findByWord("testUser2_1", author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByWord_emptyResult() {
        User author = new User(2L, "user2", "user2Pass");

        DictionaryElement result = dictionaryDao.findByWord("NoSuchWordHere", author);

        assertNull(result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dataSet/afterChange/addElement.xml")
    public void createWord() {
        User author = new User(2L, "user2", "user2Pass");
        LocalDate creationDate = LocalDate.parse("2019-01-01");

        DictionaryElement element = new DictionaryElement();
        element.setWord("testUser2_3");
        element.setTranslation("тест2_3");
        element.setAuthor(author);
        element.setCreationDate(creationDate);

        dictionaryDao.addDictionaryElement(element);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dataSet/afterChange/removeElement.xml")
    public void deleteWord() {
        User author = new User(2L, "user2", "user2Pass");

        String wordToDelete = "testUser2_2";

        dictionaryDao.removeDictionaryElement(wordToDelete, author);
    }

}
