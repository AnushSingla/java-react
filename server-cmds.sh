#!/usr/bin/env bash
#!/bin/bash

echo "Pulling image: anushsingla/java-react:$IMAGE_TAG"
docker pull anushsingla/java-react:$IMAGE_TAG

echo "Restarting app with new image..."
IMAGE_TAG=$IMAGE_TAG docker-compose up -d

echo "✅ Deployment done"
