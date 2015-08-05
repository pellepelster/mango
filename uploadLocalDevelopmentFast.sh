#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
$DIR/gradlew --build-file mango-gradle-plugin/build.gradle clean uploadLocalDevelopment
$DIR/gradlew --build-file mango-build/build.gradle -PskipCompileGwt=true -PskipPdeBuild=true clean uploadLocalDevelopment
