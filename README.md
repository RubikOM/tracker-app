## English smart dictionary

#### This app started as a Spring MVC + Hibernate web project, now it migrated to Spring boot and Spring Data. 
#### Application has several major features. 
#### It started as platform to create a word card and being able to import it in needed format.
#### Then I added side API which consists 23 theme based dictionaries for user to choose.
#### As my next step I decided to solve previous API weakness - it can only translate 1 word, so loses context in terms of text or even sentence translations.
#### Decision - another API :D. It works greatly with large texts, but has its own limitations - it can't take more than 500 characters in one request (hello multithreading)
#### Lastly, application grew with character recognition module. It's based on machine learning. This part parses input image and responses with text (for now, only English one)
### Then it all came together with previous 2 modules, so now you can upload an image, application will recognise text on it
### and will response you with recognised English text, it's translated version and several suggested words (based on user interests)

App has several parts. Word cards part relying on dictionary API, which helps user to autofill important for 
cards fields (i.e. translation, transcription and examples and their translations both).
After creating a word, app persists it into DB (MySQL). Its done to provide user
ability to import his created words anytime. Downloaded text file is valid for side word
cards application. Machine learning part enables user to upload an image (i.e. scan or photo of interested text)
to receive text and its translation. This part isn't 100% accurate and works best with printed text.

