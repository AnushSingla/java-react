def gv 
pipeline{
    agent any
    tools{
        maven 'maven-app'
    }
    stages{
        stage("init"){
         script{
            gv = load "script.groovy"
         }
        }
        stage("buildjar"){
            steps{
                script{
                    gv.buildJar()
                }
            }
        }
        stage("build image"){
            steps{
                script{
                    gv.buildImage()
                }
            }
        }
        stage("deploy"){
            steps{
                script{
                    gv.deployApp()
                }
            }
        }
    }
}

  