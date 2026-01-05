pipeline {
    agent any

    stages {

        stage('Build with Maven') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-17'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

       stage('Build Docker Image') {
    agent {
        docker {
            image 'docker:26-cli'
            args '''
              -v /var/run/docker.sock:/var/run/docker.sock
              -e DOCKER_CONFIG=$WORKSPACE/.docker
            '''
        }
    }
    steps {
        sh '''
          mkdir -p $WORKSPACE/.docker
          docker build -t ai-chatbot:latest .
        '''
    }
}


        stage('AWS CLI Check') {
    agent {
        docker {
            image 'amazon/aws-cli'
            args '''
              --entrypoint=""
              -e AWS_ACCESS_KEY_ID
              -e AWS_SECRET_ACCESS_KEY
              -e AWS_DEFAULT_REGION=us-east-1
            '''
        }
    }
    steps {
        withCredentials([
            string(credentialsId: 'aws-access-key', variable: 'AWS_ACCESS_KEY_ID'),
            string(credentialsId: 'aws-secret-key', variable: 'AWS_SECRET_ACCESS_KEY')
        ]) {
            sh 'aws sts get-caller-identity'
        }
    }
}

