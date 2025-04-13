pipeline {
    agent any

    stages {
        stage('📦 Checkout GitHub') {
            steps {
                git branch: 'AssilBelhaj-4Twin1-G2 ', url: 'https://github.com/MeryemBoukraa/4TWIN1-G2-kaddem.git'
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
    }
}
