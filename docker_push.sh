#!/bin/bash

cd ${HOME} || return
echo "${DOCKER_PASSWORD}" | docker login --username "${DOCKER_USERNAME}" --password-stdin
docker build --build-arg PROJECT_VERSION=${PROJECT_VERSION} --build-arg HOME=${HOME}  -t travis-ci-build-devops .
docker images
docker tag travis-ci-build-devops $DOCKER_USERNAME/travis-ci-build-devops:${PROJECT_VERSION}
docker push $DOCKER_USERNAME/travis-ci-build-devops:${PROJECT_VERSION}
