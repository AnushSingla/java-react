#!/bin/bash
cd ~/java-react

# Stop old container if running
docker rm -f java-react || true

# Pull the new image (IMAGE_TAG is passed by Jenkins)
docker pull anushsingla/java-react:$IMAGE_TAG

# Update docker-compose.yaml to use the correct image tag
sed -i "s#image: anushsingla/java-react:.*#image: anushsingla/java-react:$IMAGE_TAG#" docker-compose.yaml

# Start container with docker-compose
docker-compose up -d
