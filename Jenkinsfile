pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven (Docker)') {
    steps {
        sh '''
          docker run --rm \
            -v "$PWD":/app \
            -v "$HOME/.m2":/root/.m2 \
            maven:3.9.6-eclipse-temurin-17 \
            mvn -f /app/ai-chatbot/ai-chatbot/pom.xml clean package -DskipTests
        '''
    }
}


        stage('Build Docker Image') {
            steps {
                sh '''
                  docker build -t ai-chatbot:latest .
                '''
            }
        }
    }

    post {
        success {
            echo '✅ CI pipeline successful'
        }
        failure {
            echo '❌ CI pipeline failed'
        }
    }
}
