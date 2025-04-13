pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'http://localhost:9000'
        SONARQUBE_TOKEN = credentials('jenkins-sonar') 
        NEXUS_REPO_URL = 'http://localhost:8081/repository/maven-releases/'
    }

    stages {
        stage('Build & Test') {
            parallel {
                stage('Build Eureka') {
                    steps {
                        dir('eureka-server') {
                            sh 'mvn clean compile package'
                        }
                    }
                }
                stage('Build API Gateway') {
                    steps {
                        dir('api-gateway') {
                            sh 'mvn clean compile package'
                        }
                    }
                }
                stage('Build Department Service') {
                    steps {
                        dir('department-service') {
                            sh 'mvn clean compile package'
                        }
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            parallel {
                stage('Eureka Sonar') {
                    steps {
                        dir('eureka-server') {
                            withCredentials([string(credentialsId: 'jenkins-sonar', variable: 'SONARQUBE_TOKEN')]) {
                                sh "mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dsonar.host.url=$SONARQUBE_SERVER"
                            }
                        }
                    }
                }
                stage('Gateway Sonar') {
                    steps {
                        dir('api-gateway') {
                            withCredentials([string(credentialsId: 'jenkins-sonar', variable: 'SONARQUBE_TOKEN')]) {
                                sh "mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dsonar.host.url=$SONARQUBE_SERVER"
                            }
                        }
                    }
                }
                stage('Department Sonar') {
                    steps {
                        dir('department-service') {
                            withCredentials([string(credentialsId: 'jenkins-sonar', variable: 'SONARQUBE_TOKEN')]) {
                                sh "mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dsonar.host.url=$SONARQUBE_SERVER"
                            }
                        }
                    }
                }
            }
        }

        stage('Deploy to Nexus') {
            parallel {
                stage('Deploy Eureka') {
                    steps {
                        dir('eureka-server') {
                            sh 'mvn deploy -Dmaven.test.skip=true'
                        }
                    }
                }
                stage('Deploy Gateway') {
                    steps {
                        dir('api-gateway') {
                            sh 'mvn deploy -Dmaven.test.skip=true'
                        }
                    }
                }
                stage('Deploy Department') {
                    steps {
                        dir('department-service') {
                            sh 'mvn deploy -Dmaven.test.skip=true'
                        }
                    }
                }
            }
        }
 stage('Build Docker Image') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    sh 'docker-compose push'
                }
            }
        }

    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}