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
                        dir('Back-eurika') {
                            sh 'mvn clean compile package'
                        }
                    }
                }
                stage('Build API Gateway') {
                    steps {
                        dir('ApiGateWay') {
                            sh 'mvn clean compile package'
                        }
                    }
                }
                stage('Build Department Service') {
                    steps {
                        dir('departement') {
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
                        dir('Back-eurika') {
                            withCredentials([string(credentialsId: 'jenkins-sonar', variable: 'SONARQUBE_TOKEN')]) {
                                sh "mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dsonar.host.url=$SONARQUBE_SERVER"
                            }
                        }
                    }
                }
                stage('Gateway Sonar') {
                    steps {
                        dir('ApiGateWay') {
                            withCredentials([string(credentialsId: 'jenkins-sonar', variable: 'SONARQUBE_TOKEN')]) {
                                sh "mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dsonar.host.url=$SONARQUBE_SERVER"
                            }
                        }
                    }
                }
                stage('Department Sonar') {
                    steps {
                        dir('departement') {
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
                        dir('Back-eurika') {
                            sh 'mvn deploy -Dmaven.test.skip=true'
                        }
                    }
                }
                stage('Deploy Gateway') {
                    steps {
                        dir('ApiGateWay') {
                            sh 'mvn deploy -Dmaven.test.skip=true'
                        }
                    }
                }
                stage('Deploy Department') {
                    steps {
                        dir('departement') {
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
            stage('Pull and Run on Vagrant VM') {
            steps {
                script {
                    sh """
                        
                        docker-compose pull
                        
                        docker-compose up -d
                        EOF
                    """
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