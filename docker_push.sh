#!/bin/bash

ls
ls dockerbuild
ls dockerbuild/gen_dockerfile
ls dockerbuild/gen_dockerfile/files

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
#docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD"
echo $TRAVIS_BUILD_DIR

docker build -t travis-ci-build-devops  ${TRAVIS_BUILD_DIR}/dockerbuild/gen_dockerfile/files
docker images
docker tag travis-ci-build-devops $DOCKER_USERNAME/travis-ci-build-devops
docker push $DOCKER_USERNAME/travis-ci-build-devops