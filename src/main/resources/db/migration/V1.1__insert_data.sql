INSERT INTO INTERESTS(user_id, dictionary_id, priority)
VALUES (1, 18, 1),
       (1, 20, 2),
       (1, 17, 3),
       (2, 27, 1),
       (2, 20, 2),
       (2, 17, 3);

INSERT INTO DICTIONARY_ELEMENTS(id, word_in_english, transcription, translation, example, example_translation,
                                creation_date, user_id)
VALUES (1, 'test', '[tezt]', 'тест', 'testTestTest', 'тестТестТест', '2019-01-01', 3),
       (2, 'test', '[tezt]', 'тест', 'testTestTest', 'тестТестТест', '2019-01-01', 1);