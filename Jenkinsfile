pipeline{
    agent any
    options{
    buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    tools{maven 'maven_3.8.8'}

     environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
      }

    stages{
        stage('Code Compilation'){
            steps{
                sh 'mvn clean compile'
            }
        }

        stage('Code Test'){
            steps{
                sh 'mvn clean test'
            }
        }

        stage('Code Package'){
            step{
                sh 'mvn clean package'
            }
        }

        stage('Building and Tag Docker Image'){
            step{
                sh 'docker build -t mominhuzaifa/nexus-deploy .'
                sh 'docker build -t nexus-deploy .'
            }
        }

        stage('Push image to docker hub'){
            step{
                script {
                        withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerhub')]){
                        echo "Login to DockerHub : IN PROGRESS"
                        sh 'docker login docker.io -u mominhuzaifa -p ${dockerhub}'
                        echo "Push Docker Image to DockerHub : IN PROGRESS"
                        sh 'docker push mominhuzaifa/nexus-deploy:latest'
                        echo "Push Docker Image to DockerHub : COMPLETED"
                        }
            }
        }

        stage('Push Docker Image to Nexus'){
            step{
                script {
                        withCredentials([usernamePassword(credentialsId: 'nexus-user-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
                        sh 'docker login http://13.233.227.174:8085/repository/docker-hosted-repo/ -u admin -p ${PASSWORD}'
                        echo "Push Docker Image to Nexus : IN PROGRESS"

                        sh 'docker push 13.233.227.174:8085/nexus-deploy'
                        echo "Push Docker Image to Nexus : COMPLETED"
                        }
            }
        }

        stage('Delete docker image from Jenkins'){
            step{
                script{
                sh "docker rmi -f $(docker images -q)"
            }
        }
    }
}