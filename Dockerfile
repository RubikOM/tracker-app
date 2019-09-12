FROM openjdk:8-jdk-alpine
COPY ./build/libs/* ./EnglishSmartDictionary-1.3-SNAPSHOT.war
EXPOSE 8888
CMD ["java","-jar","EnglishSmartDictionary-1.3-SNAPSHOT.war"]
