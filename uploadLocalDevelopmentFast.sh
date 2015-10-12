#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
$DIR/gradlew --build-file $DIR/mango-gradle-plugin/build.gradle clean uploadLocalDevelopment
$DIR/gradlew --build-file $DIR/mango-build/build.gradle -PskipCompileGwt=true -PskipPdeBuild=true clean uploadLocalDevelopment
