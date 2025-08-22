def buildJar(){
    echo "Building Application"
    sh 'mvn package'
}

def buildImage(){
    sh 'docker build -t anushsingla/java-react:jma-2.0 .'
    withCredentials([usernamePassword(credentialsId:'docker-hub-repo',passwordVariable:'PASS',usernameVariable:'USER')]){
        sh 'echo $PASS | docker login -u $USER --password-stdin'
    }
    sh 'docker push anushsingla/java-react:jma-2.0'
}

def deployApp(){
    echo "Deploying App"
    
}

return this