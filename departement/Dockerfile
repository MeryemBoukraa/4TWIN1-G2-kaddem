FROM openjdk:17
EXPOSE 8090
ADD target/departementMicroService-*.jar departementService.jar
ENTRYPOINT ["java","-jar","departementService.jar"]