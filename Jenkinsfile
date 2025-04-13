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
        dir('Back-university1.1') {  // Ajout de l'option dir pour spécifier le bon répertoire
            sh 'mvn test -Dtest=UniversiteServiceImplTest'
        }
    }
}

stage('Run Tests') {
    steps {
        dir('Back-university1.1') {  // Ajout de l'option dir ici aussi
            sh 'mvn test'
        }
    }
}

stage('Building image') {
    steps {
        dir('Back-university1.1') {  // Assurer que tu es bien dans le bon répertoire avant de construire l'image
            sh 'docker build -t meryemboukraa/meryemboukraa-g2-kaddem:1.0.0 .'
        }
          dir('Back-eurika') {
                sh 'docker build -t yosrba/eureka:1.0 .'
                }

                dir('backgetway/ApiGetWay') {
                sh 'docker build -t yosrba/apigetway:1.0 .'
                }
    }
}

stage('Check Docker Version') {
    steps {
        sh 'docker --version'  // Aucune modification nécessaire ici
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
            '''
        }
    }
}


// stage('Run Docker Compose') {
//     steps {
//         // Utilisation de la syntaxe moderne (docker compose)
//         // --build pour reconstruire si nécessaire
//         // --force-recreate pour éviter les conflits
//         sh 'docker compose up -d --build --force-recreate'
//     }
// }



     }
}
