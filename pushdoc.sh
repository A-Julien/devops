#!/bin/bash

# Get to the Travis build directory, configure git and clone the repo
pwd
cd $HOME
ls -la
pwd
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
#git clone --quiet --branch=gh-pages https://"${GH_TOKEN}"@github.com/A-Julien/devops gh-pages > /dev/null
#git clone --quiet --branch=gh-pages https://A-Julien/devops gh-pages > /dev/null
git clone --quiet --branch=gh-pages https://"${GH_TOKEN}"@github.com:A-Julien/devops.git gh-pages > /dev/null

# Commit and Push the Changes
cd gh-pages
git rm -rf ./javadoc
cp -Rf ../target/site ./javadoc
git add -f .
git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git push -fq origin gh-pages > /dev/null
