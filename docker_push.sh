#!/bin/bash

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
#docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD"

docker build -t travis-ci-build-devops  gen_dockerfile/files
docker images
docker tag travis-ci-build-devops $DOCKER_USERNAME/travis-ci-build-devops
docker push $DOCKER_USERNAME/travis-ci-build-devops