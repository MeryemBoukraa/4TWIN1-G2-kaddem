FROM openjdk:17
EXPOSE 8082
ADD target/Ressource-0.0.1-SNAPSHOT.jar Ressource.jar
ENTRYPOINT ["java" , "-jar" , "Ressource.jar"]