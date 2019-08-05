## Helper web project

#### This is a Spring MVC + Hibernate web project. It has many purposes. 
### Application started as platform to create a word card and being able to import it in needed format.
### Then I added side API which consists 23 theme based dictionaries for user to choose.
### Lastly, application grew with character recognition module. It's based on machine learning and parses input image in order to create a resulting string.

App has several parts. Word cards part relying on dictionary API, which helps user to autofill important for 
cards fields (i.e. translation, transcription and examples and their translations both).
After creating a word, app persists it into DB (MySQL). Its done to provide user
ability to import his created words anytime. Downloaded text file is valid for side word
cards application. Machine learning part enables user to upload an image (i.e. scan or photo of interested text)
to receive text and its translation. This part isn't 100% accurate and works best with printed text.

