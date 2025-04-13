pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'M2_HOME'
        DOCKER_IMAGE = 'assilbelhaj/kassil'
        DOCKER_TAG = 'latest'
    }

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('Checkout GitHub') {
            steps {
                git branch: 'AssilBelhaj-4Twin1-G2', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('Maven Clean Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Maven Sonarqube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.token=squ_b0361c8f414b97c3eb1cdcd8737a26dc80b9c146 -Dmaven.test.skip=true'
                }
            }
        }

        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh 'mvn test -Dtest=EquipeServiceImplTest'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Run All Tests') {
            steps {
                sh 'mvn test'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package App') {
            steps {
                sh 'mvn package -Dtest=EquipeServiceImplTest'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
    }
}
