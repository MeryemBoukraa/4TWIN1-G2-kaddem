pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        DB_URL = 'jdbc:mysql://localhost:3306/microservice_ressource'  
        DB_USER = 'root'  
        DB_PASSWORD = 'root'  
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

        stage('Start MySQL Container') {
            steps {
                script {
                    // Démarre un conteneur MySQL pour les tests d'intégration
                    sh '''
                    docker run --name mysql-test -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=microservice_ressource -p 3306:3306 -d mysql:5.7
                    '''
                    // Attendre que MySQL soit prêt avant de continuer
                    sh 'sleep 20'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test -Dtest=RessourceServiceImplTest'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('MVN Sonarqube') {
             steps {
                 sh 'mvn sonar:sonar  -Dsonar.token=squ_d8943a160df3e830d68db54aea1a4fefc6d22f9a -Dmaven.test.skip=true'
             }
        }

        stage('Install') {
            steps {
                sh 'mvn install'
            }
        }

        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

         stage('Stop MySQL Container') {
            steps {
                script {
                    // Arrêter et supprimer le conteneur MySQL
                    sh 'docker stop mysql-test'
                    sh 'docker rm mysql-test'
                }
            }
        }

        stage('Building image') {
            steps {
                sh 'docker build -t yosrba/YosrBenAmor-G2-Kaddem:1.0.0 .'
            }
        }

        stage('Deploy Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                }
                sh 'docker push yosrba/YosrBenAmor-G2-Kaddem:1.0.0'
            }
        }

  /*      stage('Run Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }*/
    }
}
