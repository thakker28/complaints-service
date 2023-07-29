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
             echo "Build"
                sh 'mvn --version'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage("Unit Test"){
            steps{
            echo "Running Unit Tests"
                sh 'mvn clean test'
            }
        }

        stage("Docker Image"){
            steps{
            echo "Building Docker Image"
                script{
                    docker.build("complaints-service:${env.BUILD_TAG}")
                    }
            }
        }

        stage("Push Image"){
            echo "pushing the image to container registry"
            steps{
            sh 'docker push  gcr.io/burner-prathakk1/complaints-service:${env.BUILD_TAG}'
            }
        }
    }
}
