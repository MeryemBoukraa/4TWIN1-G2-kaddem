FROM openjdk:17
EXPOSE 8082
ADD target/Ressource-0.0.1.jar 4Twin-G2-Kaddem.jar
ENTRYPOINT ["java" , "-jar" , "4Twin-G2-Kaddem.jar"]