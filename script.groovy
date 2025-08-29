import java.net.URLEncoder

def deployApp() {
    echo "Deploying App"
}

def commitv(String newVersion) {
    echo "Committing version changes to git"

    withCredentials([usernamePassword(credentialsId: "ec80911f-ca2d-4c90-aabe-a4aa00586d02", usernameVariable: "USER", passwordVariable: "TOKEN")]) {
        def encodedToken = URLEncoder.encode(TOKEN, "UTF-8")

        sh """
            git config --global user.email "singlaanush18@gmail.com"
            git config --global user.name "Anush"
        """

        // Skip committing if triggered by previous [ci skip] commit
        def skipBuild = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim().contains('[ci skip]')
        if (skipBuild) {
            echo "Build triggered by version bump [ci skip]. Skipping..."
            return
        }

        sh "git remote set-url origin https://${USER}:${encodedToken}@github.com/AnushSingla/java-react.git"
        sh "git add ."
        sh "git commit -m 'ci: bump version to ${newVersion} [ci skip]' || echo 'No changes to commit'"
        sh "git pull origin jenkins-build --rebase || true"
        sh "git push origin HEAD:jenkins-build"
    }
}

return this
