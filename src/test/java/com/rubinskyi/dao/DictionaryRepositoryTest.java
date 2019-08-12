package com.rubinskyi.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.dao.DictionaryRepository;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
public class DictionaryRepositoryTest {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findAllByAuthor() {
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

        List<DictionaryElement> result = dictionaryRepository.findAllByAuthor(author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElementsLarge.xml")
    public void findFirst10ByAuthor() {
        User author = new User(2L, "user2", "user2Pass");
        List<DictionaryElement> result = dictionaryRepository.findFirst10ByAuthor(author);

        assertEquals(10, result.size());
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findAllByAuthor_emptyList() {
        User author = new User(100500L, "NoSuchUserHere", "NoSuchUserHere");

        List expectedResult = new ArrayList();
        List<DictionaryElement> result = dictionaryRepository.findAllByAuthor(author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByWordAndAuthor() {
        User author = new User(2L, "user2", "user2Pass");
        LocalDate creationDate = LocalDate.parse("2019-01-02");

        DictionaryElement expectedResult = new DictionaryElement();
        expectedResult.setWord("testUser2_1");
        expectedResult.setTranslation("тест2_1");
        expectedResult.setAuthor(author);
        expectedResult.setCreationDate(creationDate);

        DictionaryElement result = dictionaryRepository.findByWordAndAuthor("testUser2_1", author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByWordAndAuthor_emptyResult() {
        User author = new User(2L, "user2", "user2Pass");

        DictionaryElement result = dictionaryRepository.findByWordAndAuthor("NoSuchWordHere", author);

        assertNull(result);
    }
}
