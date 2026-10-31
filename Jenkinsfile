pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'kateashley/hello'
        IMAGE_TAG = 'latest'
        APP_PORT = '8080'
        EC2_HOST = '18.191.146.210'
        PATH+DOCKER = '/usr/local/bin'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${DOCKERHUB_REPO}:${IMAGE_TAG} .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker push ${DOCKERHUB_REPO}:${IMAGE_TAG}

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(credentials: ['ec2-ssh']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            docker pull ${DOCKERHUB_REPO}:${IMAGE_TAG} &&
                            (docker rm -f hello || true) &&
                            docker run -d --name hello \
                                -p 9090:${APP_PORT} \
                                ${DOCKERHUB_REPO}:${IMAGE_TAG}
                        "
                    '''
                }
            }
        }
    }
}