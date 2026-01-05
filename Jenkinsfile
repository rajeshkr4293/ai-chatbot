stage('Build & Push to ECR') {
    steps {
        withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: 'aws-credentials'
        ]]) {
            sh '''
            aws --version

            aws ecr get-login-password --region us-east-1 \
            | docker login --username AWS --password-stdin 582360921079.dkr.ecr.us-east-1.amazonaws.com

            docker build -t ai-chatbot:latest .

            docker tag ai-chatbot:latest \
            582360921079.dkr.ecr.us-east-1.amazonaws.com/ai-chatbot:latest

            docker push \
            582360921079.dkr.ecr.us-east-1.amazonaws.com/ai-chatbot:latest
            '''
        }
    }
}
