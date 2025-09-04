#!/bin/bash
cd ~/java-react

# Stop old container if running
docker rm -f java-react || true

# Pull the new image (IMAGE_TAG is passed by Jenkins)
docker pull anushsingla/java-react:$IMAGE_TAG

# Start container with docker-compose
IMAGE_TAG=$IMAGE_TAG docker-compose up -d
