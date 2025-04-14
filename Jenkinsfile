pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {

        stage('GIT') {
            steps {
                git branch: 'Oussamaawledsalem-4Twin1-G2', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
                dir('Eureka') {
                sh 'mvn clean compile'
                }
                dir('gateway') {
                sh 'mvn clean compile'
                }
            }
        }

        stage('Maven Clean Install') {
            steps {
                sh 'mvn clean install -DskipTests=true'
                 dir('Eureka') {
                                sh 'mvn install -DskipTests'
                                }
                                dir('gateway') {
                                sh 'mvn install -DskipTests'
                                }
            }
        }

       stage('Building Image') {
    steps {
        script {
            // 🧹 Remove old image if it exists
            sh '''
                if docker images | grep -q "oussamaawledsalem/formation"; then
                    docker rmi oussamaawledsalem/formation:2.0.0 || true
                fi
            '''
            sh 'mvn verify test'
           // Clean up named containers if they already exist
           sh 'docker rm -f eureka formation apigetway || true'

           // Clean up any orphaned/stuck containers
           sh 'docker-compose down --remove-orphans'

           // Optional: Clean dangling images, volumes, etc.
           sh 'docker system prune -af'

           // Build and start
           sh 'docker-compose up -d --build'

           // Show running containers
           sh 'docker ps'


        }
    }
}


        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                    sh 'docker push oussamaawledsalem/formation:1.0'
                    sh 'docker push oussamaawledsalem/eurika:1.0'
                    sh 'docker push oussamaawledsalem/apigetway:1.0'
                }
            }
        }



stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('scanner') {
            withCredentials([string(credentialsId: 'scanner', variable: 'TOKEN')]) {
                sh '''
                    mvn sonar:sonar \
                    -Dsonar.token=$TOKEN \
                    -Dsonar.projectKey=formation \
                    -Dsonar.sources=src/main/java \
                    -Dsonar.tests=src/test/java \
                    -Dsonar.java.binaries=target/classes \
                    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                '''
            }
        }
    }
}



        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy '

                 dir('Eureka') {
                    sh 'mvn deploy'
                 }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline executed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
