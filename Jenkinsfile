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
                }
            }
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-credentials'
                ]]) {
                    sh '''
                      aws sts get-caller-identity
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
