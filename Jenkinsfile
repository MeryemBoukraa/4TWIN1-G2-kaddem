pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('GIT') {
            steps {
               git branch: 'YosrBenAmor-4Twin1-G2', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('Compile Stage') {
            steps {
               sh 'mvn clean compile'
            }
        }

        stage('MVN Sonarqube') {
             steps {
                 sh 'mvn sonar:sonar  -Dsonar.token=sqa_d8e839e59109540e88a52e6f9c11cc093c9c1985 -Dmaven.test.skip=true'
             }
        }

       /* stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Building image') {
            steps {
                sh 'docker build -t wafahidri/timesheet-devops:1.0.0 .'
            }
        }

        stage('Deploy Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                }
                sh 'docker push wafahidri/timesheet-devops:1.0.0'
            }
        }

        stage('Run Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }*/
    }
}
