#FROM mvn #pour automatiser la tache de clean install si on fait un changement de le code source on n'est pas nesoin de fait le clean install  manuellement
FROM openjdk:17
EXPOSE 8087
ADD target/Contrat-0.0.1-SNAPSHOT.jar Contrat.jar
ENTRYPOINT ["java" , "-jar" , "Contrat.jar"]