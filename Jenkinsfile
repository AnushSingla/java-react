#!/user/bin/env groovy
@Library("jenkins-shared-lib")
def gv 
pipeline{
    agent any
    tools{
        maven 'maven-app'
    }
    stages{
        stage("init"){
            steps{
                script{
            gv = load "script.groovy"
         }
            }
         
        }
        stage("buildjar"){
            steps{
                script{
                    buildJar()
                }
            }
        }
        stage("build image"){
            steps{
                script{
                    buildDocker()
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

  