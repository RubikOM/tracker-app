package com.rubinskyi.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.rubinskyi.config.SpringTestConfig;
import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class DictionaryElementRepositoryTest {

    @Autowired
    private DictionaryElementRepository dictionaryElementRepository;

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

        List<DictionaryElement> result = dictionaryElementRepository.findAllByAuthor(author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElementsLarge.xml")
    public void findFirst10ByAuthor() {
        User author = new User(2L, "user2", "user2Pass");
        Pageable pageable = PageRequest.of(0, 10);
        List<DictionaryElement> result = dictionaryElementRepository.findAllByAuthor(author, pageable);

        assertEquals(10, result.size());
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findAllByAuthor_emptyList() {
        User author = new User(100500L, "NoSuchUserHere", "NoSuchUserHere");

        List expectedResult = new ArrayList();
        List<DictionaryElement> result = dictionaryElementRepository.findAllByAuthor(author);

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

        DictionaryElement result = dictionaryElementRepository.findByWordAndAuthor("testUser2_1", author);

        assertEquals(expectedResult, result);
    }

    @Test
    @DatabaseSetup("/dataSet/DictionaryElements.xml")
    public void findByWordAndAuthor_emptyResult() {
        User author = new User(2L, "user2", "user2Pass");

        DictionaryElement result = dictionaryElementRepository.findByWordAndAuthor("NoSuchWordHere", author);

        assertNull(result);
    }
}
