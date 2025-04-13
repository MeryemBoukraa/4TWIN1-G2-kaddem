FROM openjdk:17
EXPOSE 8089
ADD target/Equipe-1.0.0.jar Equipe.jar
ENTRYPOINT ["java","-jar","Equipe.jar"]