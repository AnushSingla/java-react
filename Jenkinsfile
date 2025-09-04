#!/usr/bin/env groovy
@Library("jenkins-shared-lib") _

pipeline {
    agent any

    tools {
        maven 'maven-app'
    }

    stages {
        stage("Init") {
            steps {
                script {
                    // Only deployApp() and commitv() are in script.groovy
                    gv = load "script.groovy"
                }
            }
        }

        stage("Build JAR") {
            steps {
                script {
                    buildJar()  // from shared library
                }
            }
        }

        stage("Build and Push Docker Image") {
            steps {
                script {
                    def tag = env.IMAGE_TAG
                    buildDocker("anushsingla/java-react:${tag}")
                    buildLogin()
                    buildPush("anushsingla/java-react:${tag}")
                }
            }
        }

        stage("Deploy") {
            steps {
                script {
                    gv.deployApp()  // from script.groovy
                }
            }
        }

        stage("Commit Version Update") {
            steps {
                script {
                    gv.commitv(env.IMAGE_TAG)  // from script.groovy
                }
            }
        }
    }
}
