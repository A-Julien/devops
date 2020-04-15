#!/bin/bash

echo "${DOCKER_PASSWORD}" | docker login --username "${DOCKER_USERNAME}" --password-stdin

docker build -t travis-ci-build-devops  gen_dockerfile/files
docker images
docker tag travis-ci-build-devops $DOCKER_USERNAME/travis-ci-build-devops
docker push $DOCKER_USERNAME/travis-ci-build-devops