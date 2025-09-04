@Library("jenkins-shared-lib") _
def gv = load "script.groovy"

pipeline {
    agent any
    tools {
        maven 'maven-app'
    }

    stages {
        stage("buildjar") {
            steps {
                script {
                    gv.buildJar()  // gv must be loaded before this
                }
            }
        }

        stage("build and push image") {
            steps {
                script {
                    def tag = env.IMAGE_TAG
                    gv.buildDocker("anushsingla/java-react:${tag}")
                    gv.buildLogin()
                    gv.buildPush("anushsingla/java-react:${tag}")
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp()  // uses IMAGE_TAG internally
                }
            }
        }

        stage("commit version update") {
            steps {
                script {
                    gv.commitv(env.IMAGE_TAG)
                }
            }
        }
    }
}
