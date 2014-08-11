#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
$DIR/gradlew --build-file mango-build/build.gradle uploadLocalDevelopment

