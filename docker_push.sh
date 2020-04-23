#!/bin/bash

echo "---------------------"
ls
echo "---------------------"
cd ${HOME} || return
echo "---------------------"
ls
echo "---------------------"
docker build --build-arg PROJECT_VERSION=${PROJECT_VERSION} --build-arg HOME=${HOME}  -t travis-ci-build-devops . #gen_dockerfile/files
docker images
docker tag travis-ci-build-devops $DOCKER_USERNAME/travis-ci-build-devops
docker push $DOCKER_USERNAME/travis-ci-build-devops
