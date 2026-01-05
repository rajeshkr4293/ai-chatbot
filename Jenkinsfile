pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

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

        stage('AWS CLI Check') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-credentials'
                ]]) {
                    sh '''
                    docker run --rm \
                      -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
                      -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
                      -e AWS_DEFAULT_REGION=us-east-1 \
                      amazon/aws-cli aws --version
                    '''
                }
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
