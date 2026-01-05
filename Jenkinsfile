pipeline {
    agent any

    environment {
        IMAGE_NAME = 'ai-chatbot'
        DOCKER_CONFIG = "${WORKSPACE}/.docker"
    }

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
                      --entrypoint=""
                      -v /var/run/docker.sock:/var/run/docker.sock
                    '''
                }
            }
            steps {
                sh '''
                  mkdir -p "$DOCKER_CONFIG"
                  docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('AWS CLI Check') {
            agent {
                docker {
                    image 'amazon/aws-cli'
                    args '--entrypoint=""'
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
