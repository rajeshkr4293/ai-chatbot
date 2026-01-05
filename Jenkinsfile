pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {

        stage('Build with Maven') {
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
            echo '✅ CI pipeline successful'
        }
        failure {
            echo '❌ CI pipeline failed'
        }
    }
}
