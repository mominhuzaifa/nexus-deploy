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
            steps{
                sh 'mvn clean package'
            }
        }
/***
        stage('Building and Tag Docker Image'){
            steps{
                sh 'docker build -t mominhuzaifa/nexus-deploy .'
                sh 'docker build -t nexus-deploy .'
            }
        }

        stage('Push image to docker hub'){
            steps{
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
    }***/

    stage(' Docker Image Push to Amazon ECR') {
               steps {
                  script {
                     docker.withRegistry([credentialsId:'ecr:ap-south-1:ecr-creds', url:'150387322390.dkr.ecr.ap-south-1.amazonaws.com/python-docker-img']){
                     sh """
                     aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 150387322390.dkr.ecr.ap-south-1.amazonaws.com
                     docker build -t python-docker-img .
                     docker images
                     docker tag python-docker-img:latest 150387322390.dkr.ecr.ap-south-1.amazonaws.com/python-docker-img:latest
                     docker push 150387322390.dkr.ecr.ap-south-1.amazonaws.com/python-docker-img:latest
                     """
                     }
                  }
               }
            }

/***
        stage('Push Docker Image to Nexus'){
            steps{
                script {
                        withCredentials([usernamePassword(credentialsId: 'nexus-user-credentials', usernameVariable: 'jenkins-user', passwordVariable: 'nexus')]){
                        sh 'docker login -u admin -p ${nexus} http://13.233.227.174:8085/repository/docker-hosted-repo/'
                        echo "Push Docker Image to Nexus : IN PROGRESS"
                        sh 'docker tag nexus-deploy 13.233.227.174:8085/nexus-deploy:latest'
                        sh 'docker push 13.233.227.174:8085/nexus-deploy'
                        echo "Push Docker Image to Nexus : COMPLETED"
                        }
            }
        }
    }***/

        stage('Delete docker image from Jenkins'){
            steps{
                script{
                sh 'docker rmi -f $(docker images -q)'
                }
            }
        }
    }
}
