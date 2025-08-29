

def deployApp(){
    echo "Deploying App"
    
}

def commitv(String newVersion){
    echo "Committing version changes to git"
    withCredentials([usernamePassword(credentialsId:"ec80911f-ca2d-4c90-aabe-a4aa00586d02",usernameVariable:"USER"
    , passwordVariable:"TOKEN")]){

    def encodedToken = URLEncoder.encode(TOKEN, "UTF-8")
    
      sh """ git config --global user.email "singlaanush18@gmail.com"
             git config --global user.name "Anush"
      
       """
       sh "git remote set-url origin https://${USER}:${encodedToken}@github.com/AnushSingla/java-react.git"
       sh "git add ."
       sh "git commit -m 'ci: bump version to ${newVersion}'"
       sh "git push origin HEAD:jenkins-build"
    }
    
}

return this