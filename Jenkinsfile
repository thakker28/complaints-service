pipeline{
    agent any
    environment {
		dockerHome = tool 'docker'
		mavenHome = tool 'maven'
		PATH = "$dockerHome/bin:$mavenHome/bin:$PATH"
	}
    
    stages{
        stage("Build Package"){
            steps{
            
                sh "mvn --version"
                sh "mvn clean install -DskipTests"
            }
        }

        stage("Unit Test"){
            steps{
           
                sh "mvn clean test"
            }
        }

        stage("Docker Image"){
            steps{
            
                script{
                    docker.build("complaints-service:${env.BUILD_TAG}")
                    }
            }
        }

        stage("Push Image"){
            
            steps{
	   
            sh "docker push  gcr.io/burner-prathakk1/complaints-service:${env.BUILD_TAG}"
            }
        }
    }
}
