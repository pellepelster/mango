#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

GRADLE_OPTS="-Dgwt.codeserver.port=9876" $DIR/gradlew --build-file mango-build/build.gradle jettyEclipseRun