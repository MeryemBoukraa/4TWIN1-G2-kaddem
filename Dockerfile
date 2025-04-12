FROM openjdk:17
EXPOSE 8089
ADD target/formation-1.0.0.jar Formation.jar
ENTRYPOINT ["java","-jar","Formation.jar"]
