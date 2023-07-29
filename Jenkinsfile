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
            echo "PATH - $PATH"
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
                  dockerImage = docker.build("burner-prathakk1/complaints-service:${env.BUILD_TAG}")
                    }
            }
        }

        stage("Push Image"){
            
            steps{
		script{
			docker.withRegistry('https://gcr.io', 'gcr:burner-prathakk1') {
			dockerImage.push();
			dockerImage.push('latest');
		}
            }
        }
    }
 }
}	
