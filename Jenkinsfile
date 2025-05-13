pipeline {
    agent any

    environment {
        IMAGE_NAME = 'spring-tdd-bdd'
        CONTAINER_NAME = 'spring-tdd-bdd-container'
    }

    stages {
        stage('Checkout Backend Repo') {
            steps {
                echo 'Clonando el repo spring-tdd-bdd desde GitHub...'
                sh 'rm -rf spring-tdd-bdd || true' // Limpia carpeta si existe
                sh 'git clone https://github.com/ewatemberg/spring-tdd-bdd.git'
            }
        }

        stage('Build Backend Image') {
            steps {
                dir('spring-tdd-bdd') {
                    echo 'Dando permisos de ejecución a mvnw'
                    sh 'chmod +x mvnw'

                    echo 'Compilando proyecto y creando imagen Docker...'
                    sh './mvnw clean package -DskipTests'
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Run Backend Container') {
            steps {
                echo 'Levantando container Docker de la API...'
                sh 'docker run -d --rm -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME'
                echo 'Esperando a que la API levante...'
                sh 'sleep 10'
            }
        }

        stage('Run API Tests (Cucumber)') {
            steps {
                echo 'Ejecutando pruebas automatizadas con Cucumber...'
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            echo 'Deteniendo el container...'
            sh 'docker stop $CONTAINER_NAME || true'
            echo 'Publicando resultados de los tests...'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
