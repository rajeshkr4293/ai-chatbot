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
            docker run --rm \
              -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
              -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
              -e AWS_DEFAULT_REGION=us-east-1 \
              amazon/aws-cli aws --version
            '''
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
