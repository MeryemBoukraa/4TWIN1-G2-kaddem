FROM openjdk:17
EXPOSE 8089
ADD target/Kassil-0.0.1-SNAPSHOT.jar Kassil.jar
ENTRYPOINT ["java","-jar","Kassil.jar"]