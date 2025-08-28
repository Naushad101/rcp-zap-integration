pipeline {
    agent any

    environment {
        GIT_REPO_URL     = 'https://github.com/Jayesh2026/zap-rcp-test.git'
        BACKEND_APP     = "reno-rcp"
        DATACLIENT_APP  = "reno-dataclient"
        DOCKER_USERNAME  = 'jayesh2026'
        BACKEND_IMAGE    = "${DOCKER_USERNAME}/${BACKEND_APP}"
        DATACLIENT_IMAGE   = "${DOCKER_USERNAME}/${DATACLIENT_APP}"

        // Environment variables passed into zap_scan.sh
        APP_URLS    = "http://rcp-backend:8081 http://frontend:80"
        ZAP_URL     = "http://zap:8090"
        REPORTS_DIR = "zap_reports"
    }

    triggers {
        githubPush()
    }

    // tools {
    //     nodejs 'Node18'
    // }

    stages {

        stage('Checkout') {
            steps {
                git credentialsId: 'github-creds',
                    url: env.GIT_REPO_URL,
                    branch: 'main'
                sh 'git branch'
            }
        }

        stage('Set Permissions & Verify Tools') {
            steps {
                sh 'chmod +x ./gradlew'
                sh 'chmod +x ./zap_scan.sh'
                sh 'java -version'
                // sh 'node --version'
                // sh 'npm --version'
                sh 'docker --version'
                sh 'trivy --version'
            }
        }

        stage('Build All Modules') {
            steps {
                sh './gradlew clean build -x test'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'backend/build/libs/*.jar', fingerprint: true
                    archiveArtifacts artifacts: 'dataclient/build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Backend Image') {
            steps {
                dir('backend') {
                    sh "docker build -t ${BACKEND_IMAGE}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Build Dataclient Image') {
            steps {
                dir('dataclient') {
                    sh "docker build -t ${DATACLIENT_IMAGE}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Trivy Scans') {
            steps {
                sh """
                    trivy image --timeout 10m --scanners vuln --format template --template "@$TRIVY_TEMPLATE" -o trivy-${BACKEND_APP}.html ${BACKEND_IMAGE}:${BUILD_NUMBER}
                    trivy image --timeout 10m --scanners vuln --format template --template "@$TRIVY_TEMPLATE" -o trivy-${DATACLIENT_APP}.html ${DATACLIENT_IMAGE}:${BUILD_NUMBER}
                """
            }
            post {
                always {
                    archiveArtifacts artifacts: 'trivy-*.html', fingerprint: true
                }
            }
        }

        stage('Save Docker Images as Tar') {
            steps {
                sh "docker save -o ${BACKEND_APP}-${BUILD_NUMBER}.tar ${BACKEND_IMAGE}:${BUILD_NUMBER}"
                sh "docker save -o ${DATACLIENT_APP}-${BUILD_NUMBER}.tar ${DATACLIENT_IMAGE}:${BUILD_NUMBER}"
            }
            post {
                success {
                    archiveArtifacts artifacts: '*.tar', fingerprint: true
                }
            }
        }
        
        // stage('Push Images to DockerHub') {
        //     steps {
        //         withCredentials([string(credentialsId: 'docker-credentials', variable: 'DOCKER_AUTH')]) {
        //             sh 'echo $DOCKER_AUTH | docker login -u ${DOCKER_USERNAME} --password-stdin'

        //             sh "docker tag ${BACKEND_IMAGE}:${BUILD_NUMBER} ${BACKEND_IMAGE}:latest"
        //             sh "docker tag ${DATACLIENT_IMAGE}:${BUILD_NUMBER} ${DATACLIENT_IMAGE}:latest"

        //             sh "docker push ${BACKEND_IMAGE}:${BUILD_NUMBER}"
        //             sh "docker push ${BACKEND_IMAGE}:latest"

        //             sh "docker push ${DATACLIENT_IMAGE}:${BUILD_NUMBER}"
        //             sh "docker push ${DATACLIENT_IMAGE}:latest"
        //         }
        //     }
        // }

        stage('Deploy with Docker Compose') {
            steps {
                //sh 'docker compose down || true'
                sh 'docker compose up -d --build'
            }
        }

        stage('Wait for ZAP Ready') {
            steps {
                script {
                sh "sleep 60"
                def maxRetries = 10
                def count = 0
                def zapReady = false
                while (count < maxRetries && !zapReady) {
                    def status = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${env.ZAP_URL}", returnStdout: true).trim()
                    echo "ZAP check attempt ${count + 1}: HTTP $status"
                    if (status == '200') {
                    zapReady = true
                    echo "ZAP is running and healthy"
                    } else {
                    count++
                    sleep(time: 10, unit: 'SECONDS')
                    }
                }
                if (!zapReady) {
                    error 'ZAP did not become ready in time!'
                }
                }
            }
        }

        stage('Run ZAP Security Scan') {
            steps {
                sh "./zap_scan.sh" 
            }
            post {
                success {
                    archiveArtifacts artifacts: "${env.REPORTS_DIR}/*", allowEmptyArchive: false
                }
            }
        }        

    }

    post {
        always {
            echo 'Cleaning up old Docker tags...'
            sh '''#!/bin/bash
            
            docker compose down zap

            # Get all images for our project
            ALL_IMAGES=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep "^${DOCKER_USERNAME}/reno-")
            
            # AWS ECR
            # ALL_IMAGES=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep "${ECR_ACCOUNT_ID}.dkr.ecr.$AWS_REGION.amazonaws.com/bntcnx/reno-")

            # Process each image
            for IMAGE in $ALL_IMAGES; do
                echo "Removing $IMAGE"
                docker rmi "$IMAGE" || true
            done
            '''
            cleanWs()
        }
        success {
            echo 'Deployment Successful!'
        }
        failure {
            echo 'Deployment Failed!'
        }
    }
}