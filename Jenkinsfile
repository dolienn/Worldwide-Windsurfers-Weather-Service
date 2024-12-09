pipeline {
    agent {
        docker {
            image 'maven:3.9.6-openjdk-17'
        }
    }

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Test') {
             steps {
                sh "mvn test"
            }
        }
        stage('Deploy') {
             steps {
                sh "mvn clean heroku:deploy"
            }
        }
    }

    post {
        success {
            echo 'Pipeline successfully completed.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
