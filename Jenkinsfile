pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'assilbelhaj'
        IMAGE_NAME = "${DOCKERHUB_USER}/equipe"
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'admin'
        DOCKERHUB_TOKEN = credentials('dockerhub_PSW')
    }

    stages {
        stage('📦 Checkout GitHub') {
            steps {
                git branch: 'main', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('🔍 Maven Version Check') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('🧹 Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('⚙️ Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('🧪 Unit Tests (JUnit/Mockito)') {
            steps {
                sh 'mvn --batch-mode test'
            }
        }

        stage('📤 Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('🔎 SonarQube Analysis') {
            steps {
                sh """
                  mvn sonar:sonar \
                  -Dsonar.login=${SONAR_LOGIN} \
                  -Dsonar.password=${SONAR_PASSWORD}
                """
            }
        }

        stage('🐳 Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('🔐 Docker Login') {
            steps {
                sh "echo ${DOCKERHUB_TOKEN} | docker login -u ${DOCKERHUB_USER} --password-stdin"
            }
        }

        stage('📦 Push Docker Image') {
            steps {
                sh "docker push ${IMAGE_NAME}"
            }
        }

        stage('🚀 Run App with Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('🧹 Clean Up Docker Image') {
            steps {
                sh "docker rmi -f ${IMAGE_NAME} || true"
            }
        }

        stage('📧 Send Success Email') {
            steps {
                mail to: 'assil.belhaj@esprit.tn',
                     subject: '✅ DevOps Pipeline Executed Successfully',
                     body: '''Hello Assil,


            }
        }
    }
}
