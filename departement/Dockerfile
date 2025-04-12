FROM openjdk:17
EXPOSE 8090
ADD target/departementMicroService-0.0.1-SNAPSHOT.jar departementService.jar
ENTRYPOINT ["java","-jar","departementService.jar"]