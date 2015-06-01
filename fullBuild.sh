#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/gradlew --build-file mango-gradle-plugin/build.gradle uploadLocalDevelopment
$DIR/gradlew --build-file mango-build/build.gradle clean build uploadLocalDevelopment

rm -rf $DIR/project1
cat $DIR/bootstrap.sh | bash -s org.example.Project1

$DIR/gradlew --refresh-dependencies --build-file ./project1/project1-build/build.gradle build jettyEclipseRun
