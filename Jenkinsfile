pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "gestionepensione-app"
        DOCKER_CONTAINER = "gestionepensione-container"
        SERVER_IP = "192.168.68.116"
        SERVER_USER = "simone"
        DEPLOY_PATH = "/home/${SERVER_USER}/app"
        GIT_CREDENTIALS_ID = 'github-credentials'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                sh 'rm -rf *'
            }
        }
        stage('Checkout') {
            steps {
                script {
                    checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']], // Modifica se usi un altro branch
                    userRemoteConfigs: [[
                    url: 'https://github.com/simonebarberini/GestionaleMonolitico.git',
                    credentialsId: 'github-credentials'
                ]],
                extensions: [[$class: 'CloneOption', depth: 0, noTags: false, shallow: false]]
            ])
                }
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package -DskipTests'  // Compila l'app senza eseguire i test
            }
        }

        stage('Build Docker Image') {
            steps {
                // Costruisce l'immagine del backend
                 sh 'docker build -t gestionepensione-backend -f backend/Dockerfile backend/'

                  // Costruisce l'immagine del frontend
                  sh 'docker build -t gestionepensione-frontend -f frontend/Dockerfile frontend/'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    sh 'docker tag ${DOCKER_IMAGE} your-dockerhub-username/${DOCKER_IMAGE}:latest'
                    sh 'docker push your-dockerhub-username/${DOCKER_IMAGE}:latest'
                }
            }
        }

        stage('Deploy on Remote Server') {
            steps {
                sshagent(['jenkins-ssh-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} <<EOF
                    docker stop ${DOCKER_CONTAINER} || true
                    docker rm ${DOCKER_CONTAINER} || true
                    docker pull your-dockerhub-username/${DOCKER_IMAGE}:latest
                    docker run -d --name ${DOCKER_CONTAINER} -p 8080:8080 your-dockerhub-username/${DOCKER_IMAGE}:latest
                    EOF
                    '''
                }
            }
        }
    }
}
