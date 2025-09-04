pipeline {
    agent any
    tools {
        maven 'maven-app'
    }
    stages {
        stage("buildjar") {
            steps {
                script {
                    // Call buildJar() from the loaded Groovy library
                    gv.buildJar()  // sets env.IMAGE_TAG automatically
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
                    gv.deployApp() // deployApp() uses IMAGE_TAG internally
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
