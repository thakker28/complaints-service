pipeline{
    agent any
    stages{
        stage("Build Package"){
            steps{
             echo "Build"
            }
        }

        stage("Unit Test"){
            steps{
            echo "Unit Tests"
            }
        }

        stage("Docker Image"){
            steps{
            echo "Building Image"
            }
        }

        stage("Push Image"){
            steps{
            echo "Image pushed"
            }
        }
    }
}
