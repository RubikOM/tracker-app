/*TODO problems in project
0) Caching by dictionary + add dictionary for minicard and cache it as well as all dictionaries
0*) After caching we can no more hold words in table for each concrete user -- just hold a reference in DB
2) JSP -> thymeleaf (or even react or angular for front)
6) js looks very trashy at the time, mb switch for some front - end framework
9) transactional in dao tests are not working so I did transactional in dao layer instead of service
15) Test what will happen if user not in DB in authentication time
18) spellchecker TranslationFromApiController : if word isn't correct guess what user wanted to add + Some normal way to show error (not one currently used)
19) ComprehensiveTranslationServiceLingvo makeWordValidForApi() throws to apiEntity just 1 word, need some normal logic here
29) Add Liquidbase to project.
33) Validator
34) Optionals in repository methods!!!
36) Message if password wrong
38) edit form button.
39) Tess4j rewrites my logger config, I should delete removeHandlersForRootLogger() in LoggerConfig.java in Tess4j.jar
43) https://developers.lingvolive.com/ru-ru/Help Suggests
44) Migrate to Junit5 and mockito 3
45) Interest and Dictionary repositories
46) Should rebuild SuggestedTranslationService - it now takes 5 biggest words, should take 5 less known ones

1	mike	qwerty3011
2	lia	passwordToCopy
3	testUser	testUser
*/

DROP TABLE IF EXISTS DICTIONARIES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS INTERESTS;
DROP TABLE IF EXISTS DICTIONARY_ELEMENTS;

CREATE TABLE USERS
(
    id       BIGINT auto_increment primary key,
    name     varchar(20),
    password varchar(20),
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
    foreign key (dictionary_id) references DICTIONARIES (id),
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

    foreign key (user_id) references USERS (id),
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
