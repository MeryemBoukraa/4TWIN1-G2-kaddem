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
                sh 'mvn clean compile'
             }
         }
 
 
 
        stage('MVN Sonarqube') {
              steps {
                  sh 'mvn sonar:sonar  -Dsonar.token=squ_bbe8b39c70c162231659881639677022f6a332aa -Dmaven.test.skip=true'
              }
         }
 
 
    stage('MVN Nexus') {
             steps {
                 sh 'mvn deploy -Dmaven.test.skip=true'
             }
         }
 
 
 
          stage("MOCKITO") {
             steps {
                 sh "mvn test -Dtest=mvn test -Dtest=com.example.universite.UniversiteApplicationTests"
             }
         }
 
         stage('Run Unit Tests') {
             steps {
                 sh 'mvn test -Dtest=UniversiteServiceImplTest'
             }
         }
         stage('Run Tests') {
             steps {
                 sh 'mvn test'
             }
         } 
 
 
 
 
 
         stage('Building image') {
             steps {
                 sh 'docker build -t meryemboukraa/meryemboukraa-g2-kaddem:1.0.0 .'
             }
         }
 
     
       stage('Deploy Image') {
    steps {
        withCredentials([usernamePassword(
            credentialsId: 'docker-hub-credentials',
            usernameVariable: 'DOCKER_USERNAME',
            passwordVariable: 'DOCKER_PASSWORD'
        )]) {
            // Méthode plus sécurisée que echo + pipe
            sh '''
                docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                docker push meryemboukraa/meryemboukraa-g2-kaddem:1.0.0
            '''
        }
    }
}

stage('Run Docker Compose') {
    steps {
        // Utilisation de la syntaxe moderne (docker compose)
        // --build pour reconstruire si nécessaire
        // --force-recreate pour éviter les conflits
        sh 'docker compose up -d --build --force-recreate'
    }
}
     }
}
