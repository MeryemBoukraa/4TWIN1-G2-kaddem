pipeline {
    agent any

    stages {
        stage('Checkout GitHub') {
            steps {
                git branch: 'AssilBelhaj-4Twin1-G2 ', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('Maven Clean Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
         stage('Maven Sonarqube') {
                     steps {
                   sh 'mvn sonar:sonar  -Dsonar.token=squ_b0361c8f414b97c3eb1cdcd8737a26dc80b9c146 -Dmaven.test.skip=true'
                }
         }
    stage('MVN Nexus') {
             steps {
                 sh 'mvn deploy -Dmaven.test.skip=true'
             }
         }



    }
}
