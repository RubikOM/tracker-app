USE english_dictionary;

DROP TABLE IF EXISTS DICTIONARIES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS INTERESTS;
DROP TABLE IF EXISTS DICTIONARY_ELEMENTS;

CREATE TABLE USERS
(
    id       BIGINT auto_increment primary key,
    name     varchar(30),
    password varchar(200)
);

CREATE TABLE DICTIONARIES
(
    id   BIGINT auto_increment primary key,
    name varchar(100)
);

CREATE TABLE INTERESTS
(
    id            BIGINT auto_increment primary key,
    user_id       BIGINT,
    dictionary_id BIGINT,
    priority      int,

    foreign key (user_id) references USERS (id),
    foreign key (dictionary_id) references DICTIONARIES (id)
);

CREATE TABLE DICTIONARY_ELEMENTS
(
    id                  BIGINT auto_increment primary key,
    word_in_english     varchar(100) not null,
    transcription       varchar(100),
    translation         varchar(255),
    example             varchar(255),
    example_translation varchar(255),
    creation_date       date,
    user_id             BIGINT,

    foreign key (user_id) references USERS (id)
);

insert into DICTIONARIES (name)
values ('Accounting (En-Ru)'),
       ('AmericanEnglish (En-Ru)'),
       ('Beer (En-Ru)'),
       ('Biology (En-Ru)'),
       ('Building (En-Ru)'),
       ('EconomicusBanking (En-Ru)'),
       ('EconomicusGovernment (En-Ru)'),
       ('EconomicusInsurance (En-Ru)'),
       ('EconomicusInternational (En-Ru)'),
       ('Electronics (En-Ru)'),
       ('Engineering (En-Ru)'),
       ('FinancialManagement (En-Ru)'),
       ('FinancialMarkets (En-Ru)'),
       ('ForumDictionary (En-Ru)'),
       ('Geography (En-Ru)'),
       ('Law (En-Ru)'),
       ('Learning (En-Ru)'),
       ('LingvoComputer (En-Ru)'),
       ('LingvoGrammar (En-Ru)'),
       ('LingvoUniversal (En-Ru)'),
       ('Management (En-Ru)'),
       ('Marketing'),
       ('MechanicalEngineering (En-Ru)'),
       ('MenuTranslate (En-Ru)'),
       ('Patents (En-Ru)'),
       ('Physics (En-Ru)'),
       ('Psychology (En-Ru)'),
       ('Sport (En-Ru)'),
       ('Telecoms (En-Ru)');

INSERT INTO USERS(name, password)
VALUES ('mike', '{bcrypt}$2a$10$EOpHVH1Rm2elB4n0gAH4euDY0uX8WPY1UUHu14Pf9oADYenkcO0j6'),
       ('lia', '{bcrypt}$2a$10$eznr5TwKuNAGOPYQVNOUrOusUaE7QJtv0P9FIhHzCY8qwiSdNNC8K'),
       ('testUser', '{bcrypt}$2a$10$UCmHqNm1/3BIesDTOz2anuyyD2Cs5YRP/vlxiX2DoCdEiHObEVUfi');

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