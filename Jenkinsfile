pipeline {
    agent any

    environment {
        IMAGE_NAME = 'spring-tdd-bdd'
        CONTAINER_NAME = 'spring-tdd-bdd-container'
    }

    stages {
        stage('Checkout Backend Repo') {
            steps {
                sh 'rm -rf spring-tdd-bdd || true'
                sh 'git clone https://github.com/ewatemberg/spring-tdd-bdd.git'
            }
        }

        stage('Build Backend Image') {
            steps {
                dir('spring-tdd-bdd') {
                    sh 'chmod +x mvnw'
                    sh 'mvn clean package -DskipTests'
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Run Backend Container') {
            steps {
                sh 'docker run -d --rm -p 8082:8082 --name $CONTAINER_NAME $IMAGE_NAME'
                sh 'sleep 10'
            }
        }

        stage('Run API Tests (Cucumber)') {
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
                        sh 'mvn clean test'
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker stop $CONTAINER_NAME || true'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
