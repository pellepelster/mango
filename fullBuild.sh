#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ -z "$BUILD_NUMBER" ]; then
	BUILD_NUMBER=$(date +%Y%m%d%H%M)
	echo "no build number set, using timestamp ${BUILD_NUMBER}"
fi

GRADLE_OPTIONS=""

if [ "$GRADLE_DEBUG" = true ]; then
	GRADLE_OPTIONS="${GRADLE_OPTIONS} --debug"
fi

if [ "$GRADLE_RERUN_TASKS" = true ]; then
	GRADLE_OPTIONS="${GRADLE_OPTIONS} --rerun-tasks"
fi

echo "gradle options: ${GRADLE_OPTIONS}"

VERSION_QUALIFIER=${BUILD_NUMBER}
 
GRADLE_OPTS="-Xmx1024M" $DIR/gradlew $GRADLE_OPTIONS --build-file=$DIR/mango-gradle-plugin/build.gradle -PversionQualifier=$VERSION_QUALIFIER clean build uploadReleaseBuild || exit 1;
GRADLE_OPTS="-Xmx1400M" $DIR/gradlew $GRADLE_OPTIONS --build-file=$DIR/mango-build/build.gradle -PversionQualifier=$VERSION_QUALIFIER clean build uploadReleaseBuild || exit 1;
