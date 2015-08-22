#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

GRADLE_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=y -Dgwt.codeserver.port=9876" $DIR/gradlew --build-file $DIR/mango-build/build.gradle jettyEclipseRun

