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


def commitv(String newVersion) {
    echo "📦 Committing version bump to git: ${newVersion}"

    withCredentials([usernamePassword(
        credentialsId: "ec80911f-ca2d-4c90-aabe-a4aa00586d02",
        usernameVariable: "USER",
        passwordVariable: "TOKEN"
    )]) {
        def encodedToken = URLEncoder.encode(TOKEN, "UTF-8")

        sh """
            git config --global user.email "singlaanush18@gmail.com"
            git config --global user.name "Anush"
        """

        // Check for [ci skip] in last commit
        def skipBuild = sh(
            script: "git log -1 --pretty=%B",
            returnStdout: true
        ).trim().contains('[ci skip]')

        if (skipBuild) {
            echo "Skipping commit due to [ci skip] in last message"
            return
        }

        // Push new version to jenkins-build branch
        sh """
            git remote set-url origin https://${USER}:${encodedToken}@github.com/AnushSingla/java-react.git
            git add .
            git commit -m 'ci: bump version to ${newVersion} [ci skip]' || echo 'No changes to commit'
            git pull origin jenkins-build --rebase || true
            git push origin HEAD:jenkins-build
        """
    }
}

return this
