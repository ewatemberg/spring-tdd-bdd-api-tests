pipeline {
    agent any

    environment {
        IMAGE_NAME = 'spring-tdd-bdd'
        CONTAINER_NAME = 'spring-tdd-bdd-container'
    }

    stages {
        stage('Build Backend Image') {
            steps {
                dir('../spring-tdd-bdd') {
                    sh 'mvn clean package -DskipTests'
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Run Backend Container') {
            steps {
                sh 'docker run -d --rm -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME'
                // Espera que la API levante
                sh 'sleep 10'
            }
        }

        stage('Run API Tests (Cucumber)') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker stop $CONTAINER_NAME || true'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
