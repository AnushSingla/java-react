import java.net.URLEncoder

def deployApp() {
    // Use IMAGE_TAG set by buildJar() instead of default "latest"
    def imageTag = env.IMAGE_TAG ?: "latest"

    sshagent(['aws-jenkins']) {
        sh """
            ssh -o StrictHostKeyChecking=no ec2-user@43.205.194.102 '
                cd ~/java-react
                IMAGE_TAG=${imageTag} bash server-cmds.sh
            '
        """
    }
}



def commitv() {
    // Get current version from Maven
    def currentVersion = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
    echo "Current Version: ${currentVersion}"

    // Auto-increment patch number (1.0.7 -> 1.0.8)
    def parts = currentVersion.tokenize('.')
    parts[2] = (parts[2].toInteger() + 1).toString()
    def newVersion = parts.join('.')
    echo "Bumping to: ${newVersion}"

    // Set new version in pom.xml
    sh "mvn versions:set -DnewVersion=${newVersion}"
    sh "mvn versions:commit"

    // Commit and push to git
    withCredentials([usernamePassword(credentialsId: "ec80911f-ca2d-4c90-aabe-a4aa00586d02", usernameVariable: "USER", passwordVariable: "TOKEN")]) {
        def encodedToken = URLEncoder.encode(TOKEN, "UTF-8")
        sh """
            git config --global user.email "singlaanush18@gmail.com"
            git config --global user.name "Anush"
            git remote set-url origin https://${USER}:${encodedToken}@github.com/AnushSingla/java-react.git
            git add pom.xml
            git commit -m 'ci: bump version to ${newVersion} [ci skip]' || echo 'No changes to commit'
            git push origin HEAD:main
        """
    }

    return newVersion
}

return this