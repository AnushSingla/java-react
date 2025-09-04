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
    // Read current version from pom.xml
    def currentVersion = sh(
        script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
        returnStdout: true
    ).trim()
    echo "Current version: ${currentVersion}"

    // Increment patch version
    def parts = currentVersion.tokenize('.')
    def major = parts[0].toInteger()
    def minor = parts[1].toInteger()
    def patch = parts[2].toInteger() + 1
    def newVersion = "${major}.${minor}.${patch}"
    echo "Bumping version to: ${newVersion}"

    // Update POM
    sh "mvn versions:set -DnewVersion=${newVersion}"
    sh "mvn versions:commit"

    withCredentials([usernamePassword(
        credentialsId: "ec80911f-ca2d-4c90-aabe-a4aa00586d02",
        usernameVariable: "USER",
        passwordVariable: "TOKEN"
    )]) {
        def encodedToken = URLEncoder.encode(TOKEN, "UTF-8")

        sh """
            git config --global user.email "singlaanush18@gmail.com"
            git config --global user.name "Anush"
            
            git remote set-url origin https://${USER}:${encodedToken}@github.com/AnushSingla/java-react.git
            git add .
            git commit -m 'ci: bump version to ${newVersion} [ci skip]' || echo 'No changes to commit'
            git pull origin jenkins-build --rebase || true
            git push origin HEAD:jenkins-build
        """
    }

    return newVersion
}
