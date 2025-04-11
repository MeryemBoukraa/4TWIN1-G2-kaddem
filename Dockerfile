FROM openjdk:17
EXPOSE 8082
ADD target/universite-0.0.1-SNAPSHOT.jar Universite.jar
ENTRYPOINT ["java" , "-jar" , "Universite.jar"]