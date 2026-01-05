pipeline {
    agent any

    stages {
        stage('Inspect Workspace') {
            steps {
                sh '''
                  echo "=== CURRENT DIRECTORY ==="
                  pwd
                  echo "=== LIST ROOT ==="
                  ls -la
                  echo "=== FIND pom.xml ==="
                  find . -name pom.xml
                '''
            }
        }
    }
}
