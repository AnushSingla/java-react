#!/bin/bash

# Make sure IMAGE_TAG is exported so docker-compose can use it
export IMAGE_TAG=${IMAGE_TAG:-latest}

# Stop the old containers
docker-compose down

# Pull the new image (optional, docker-compose up -d will also pull if not present)
docker-compose pull

# Start the containers with the new image
docker-compose up -d
