pipeline {
  agent any

  environment {
    MAVEN_HOME = tool 'M2_HOME'
    DOCKER_IMAGE = 'assilbelhaj/kassil'
    DOCKER_TAG = 'latest'
    NEXUS_REPO = 'http://192.168.33.10:8081/repository/maven-snapshots/'
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
        dir('Kassil') {  
          sh 'mvn clean compile'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        dir('Kassil') {  
          withSonarQubeEnv('SonarQube') {
            sh '''
              mvn sonar:sonar \
                -Dsonar.token=squ_b0361c8f414b97c3eb1cdcd8737a26dc80b9c146 \
                -Dmaven.test.skip=true
            '''
          }
        }
      }
    }

    stage('Run Unit Tests') {
      steps {
        dir('Kassil') {  
          sh 'mvn test -Dtest=EquipeServiceImplTest'
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Run All Tests') {
      steps {
        dir('Kassil') {  
          sh 'mvn test'
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('SonarQube Quality Gate') {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Package Application') {
      steps {
        dir('Kassil') {  
          sh 'mvn package -Dtest=EquipeServiceImplTest'
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Publish to Nexus') {
      steps {
        dir('Kassil') {  
          withCredentials([usernamePassword(credentialsId: 'nexus-creds', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
            sh """
              mvn deploy \\
                -DaltDeploymentRepository=nexus::default::${NEXUS_REPO} \\
                -Dnexus.user=\$NEXUS_USER \\
                -Dnexus.password=\$NEXUS_PASS
            """
          }
        }
      }
    }

    stage('Build API Gateway') {
    steps {
        dir('Back-apigetway') {
            sh 'mvn clean package -DskipTests'
        }
    }
}

    stage('Build Docker Image') {
      steps {
        dir('Kassil') {  
          script {
            docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
          }
        }
      }
    }

    stage('Push Docker Image to DockerHub') {
      steps {
        dir('Kassil') {  
          withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
            sh """
              echo "\$DOCKERHUB_PASS" | docker login -u "\$DOCKERHUB_USER" --password-stdin
              docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
            """
          }
        }
      }
    }

stage('Install Docker Compose') {
  steps {
    sh '''
      if ! docker compose version &> /dev/null
      then
        echo "Installing Docker Compose..."
        mkdir -p ~/.docker/cli-plugins/
        curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 \
          -o ~/.docker/cli-plugins/docker-compose
        chmod +x ~/.docker/cli-plugins/docker-compose
      else
        echo "Docker Compose already installed."
      fi
      docker compose version
    '''
  }
}

stage('Deploy with Docker Compose') {
  steps {
      sh 'docker compose -f docker-compose.yml up -d'
    
  }
}


    stage('Notify Team') {
      steps {
        mail to: 'oumayma.sahmim@esprit.tn',
          subject: "✅ Kassil Pipeline Success",
          body: "The pipeline completed successfully.\n\nDetails: ${env.BUILD_URL}",
           replyTo: 'no-reply@gmail.com'
      }

    }

  }

  post {
    failure {
      mail to: 'team@kassil.tn',
        subject: "❌ Kassil Pipeline Failed",
        body: "The pipeline failed.\n\nCheck the logs here: ${env.BUILD_URL}",
        replyTo: 'no-reply@kassil.tn'
    }
  }

}