pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rajeshkr4293/ai-chatbot.git'
            }
        }

        stage('Build Maven') {
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
            echo '✅ CI pipeline completed successfully'
        }
        failure {
            echo '❌ CI pipeline failed'
        }
    }
}
