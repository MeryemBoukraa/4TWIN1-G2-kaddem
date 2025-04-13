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
            }
        }

        stage('Maven Clean Install') {
            steps {
                sh 'mvn clean install -DskipTests'
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

            // 🛠️ Build the new image
            sh 'docker-compose down --remove-orphans'
            sh 'docker system prune -af'
                               // Build and run the containers
            sh 'docker-compose up -d --build'

        }
    }
}


        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                    sh 'docker push oussamaawledsalem/formation:2.0.0'
                }
            }
        }

        stage('Docker Run') {
            steps {
                script {
            // Stop and remove the container if it exists
            sh '''
                if [ "$(docker ps -aq -f name=springDevops)" ]; then
                  docker stop springDevops || true
                  docker rm springDevops || true
                fi
            '''
        }
                sh 'docker run -d --name springDevops -p 8089:8089 oussamaawledsalem/formation:2.0.0'
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
                -Dsonar.java.binaries=target/classes
                '''
            }
        }

    }
}



        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
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
