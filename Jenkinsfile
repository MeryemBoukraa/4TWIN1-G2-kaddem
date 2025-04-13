pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {

        stage('GIT') {
            steps {
                git branch: 'MeryemBoukraa-4TWIN1-G2', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('Compile Stage') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn clean compile'
                    sh 'mvn clean compile verify'
                }

                dir('Back-eurika') {
                    sh 'mvn clean compile verify'
                }

                dir('backgetway') {
                    sh 'mvn clean compile verify'
                }
            }
        }

        stage('MVN Sonarqube') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn sonar:sonar -Dsonar.token=squ_bbe8b39c70c162231659881639677022f6a332aa -Dmaven.test.skip=true'
                }
            }
        }

        stage('Install') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn install'
                }

                dir('backgetway') {
                    sh 'mvn install'
                }

                dir('Back-eurika') {
                    sh 'mvn install'
                }
            }
        }

        stage('MVN Nexus') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn deploy -Dmaven.test.skip=true'
                }
            }
        }

        stage('MOCKITO') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn test -Dtest=com.example.universite.UniversiteApplicationTests'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn test -Dtest=UniversiteServiceImplTest'
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir('Back-university1.1') {
                    sh 'mvn test'
                }
            }
        }

        stage('Building image') {
            steps {
                dir('Back-university1.1') {
                    sh 'docker build -t meryemboukraa/meryemboukraa-g2-kaddem:1.0.0 .'
                }

                dir('Back-eurika') {
                    sh 'docker build -t meryemboukraa/eureka:1.0 .'
                }

                dir('backgetway') {
                    sh 'docker build -t meryemboukraa/apigetway:1.0 .'
                }
            }
        }

        stage('Check Docker Version') {
            steps {
                sh 'docker --version'
            }
        }

        stage('Deploy Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                        docker push meryemboukraa/meryemboukraa-g2-kaddem:1.0.0
                        docker push meryemboukraa/apigetway:1.0
                        docker push meryemboukraa/eureka:1.0
                    '''
                }
            }
              stage('Docker Compose Up') {
            steps {
                script {
                    sh 'docker compose up -d'
                }
            }
        }

         stage("Run Prometheus"){
      steps{
 
        script{

        sh('docker start prometheus')

        }
      }
    }
     stage("Run Grafana"){ 
      steps{

        script{
        sh('docker start grafana')
        }
      }
        }

        // Décommente si tu veux activer docker compose
        // stage('Run Docker Compose') {
        //     steps {
        //         sh 'docker compose up -d --build --force-recreate'
        //     }
        // }

    }
}
