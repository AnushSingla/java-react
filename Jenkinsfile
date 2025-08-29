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
                    def tag = env.IMAGE_TAG
                    buildDocker "anushsingla/java-react:${tag}"
                    buildLogin()
                    buildPush "anushsingla/java-react:${tag}"
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
        stage("commit version update"){
            steps{
                script{
                    gv.commitv(env.IMAGE_TAG)
                }
            }
        }
    }
}

  