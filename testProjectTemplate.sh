#!/bin/bash

./gradlew --build-file mango-build/build.gradle :mango-project-template:uploadLocalDevelopment :mango-bootstrap:uploadLocalDevelopment
cat ./bootstrap.sh | bash -s org.example.Project1
./gradlew --refresh-dependencies --build-file ./project1/project1-build/build.gradle build jettyEclipseRun
