public class TODO {

    // TODO import DB data or release app on some free resource for gf to use.
    // TODO rebuild jsp to thymeleaf
    // TODO use TDD in project

    private final String CREATE_TABLE_ENGLISH_WORDS = "Create table english_words (id int auto_increment primary key, word_in_english varchar(40) not null, transcription varchar(40), translation varchar(100) not null, example varchar(255) example_translation varchar(255), creation_date date);";

    private final String CREATE_WORD1 = "INSERT INTO ENGLISH_WORDS(word_in_english, transcription, translation, example, example_translation, creation_date) values('test', '[test]', 'тест', 'test test test', 'тест тест тест', '2019-02-22')";
}
