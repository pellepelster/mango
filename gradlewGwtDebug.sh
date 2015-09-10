#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

JAVA_DEBUG="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=n"

GRADLE_OPTS="$JAVA_DEBUG -Dgwt.codeserver.port=9876" $DIR/gradlew --build-file $DIR/mango-build/build.gradle jettyEclipseRun

#GRADLE_OPTS="-Dgwt.codeserver.port=9876" $DIR/gradlew --build-file $DIR/mango-build/build.gradle jettyEclipseRun

