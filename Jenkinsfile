pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-17'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ai-chatbot:latest .'
            }
        }
    }

    post {
        success {
            echo '✅ CI pipeline SUCCESS'
        }
        failure {
            echo '❌ CI pipeline FAILED'
        }
    }
}
