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
        stage("build and push image"){
            steps{
                script{
                    buildDocker "anushsingla/java-react:jma-3.0"
                    buildLogin()
                    buildPush "anushsingla/java-react:jma-3.0"
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

  