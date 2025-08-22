def buildApp(){
    echo "Building Application"
}

def testApp(){
    echo "Testing the application"
}

def deployApp(){
    echo "Deploying App"
    echo "Deploying version ${params.VERSION}"
}

return this