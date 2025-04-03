FROM openjdk:17
EXPOSE 8089
ADD target/formation-0.0.1-SNAPSHOT.jar Formation.jar
ENTRYPOINT ["java","-jar","Formation.jar"]