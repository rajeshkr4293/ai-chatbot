pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-1'
        ECR_REPO = '582360921079.dkr.ecr.us-east-1.amazonaws.com/ai-chatbot'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

      stage('Build with Maven') {
    steps {
        sh 'chmod +x mvnw'
        sh './mvnw clean package -DskipTests'
    }
}



        stage('Build & Push to ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-credentials'
                ]]) {
                    sh '''
                    aws --version

                    aws ecr get-login-password --region $AWS_REGION \
                    | docker login --username AWS --password-stdin $ECR_REPO

                    docker build -t ai-chatbot:latest .

                    docker tag ai-chatbot:latest $ECR_REPO:latest

                    docker push $ECR_REPO:latest
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
