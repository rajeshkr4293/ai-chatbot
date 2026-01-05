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
                      amazon/aws-cli aws sts get-caller-identity
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
