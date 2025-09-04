stage("buildjar"){
    steps{
        script{
            buildJar()  // IMAGE_TAG is set automatically
        }
    }
}

stage("build and push image"){
    steps{
        script{
            def tag = env.IMAGE_TAG
            buildDocker("anushsingla/java-react:${tag}")
            buildLogin()
            buildPush("anushsingla/java-react:${tag}")
        }
    }
}

stage("deploy"){
    steps{
        script{
            gv.deployApp() // deployApp() uses IMAGE_TAG internally
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
