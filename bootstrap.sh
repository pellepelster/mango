#!/bin/bash

OUTPUT_DIR="."
PROJECT_FQDN_NAME=
PROJECT_NAME=
PACKAGE_NAME=

GRADLE_WRAPPER_BASE="https://raw.githubusercontent.com/pellepelster/mango/master"
BOOTSTRAP_GROUP_NAME="io.pelle.mango"
BOOTSTRAP_NAME="mango-bootstrap"

PROJECT_NAME_REGEX='(([a-z][a-z0-9]*\.)*)([A-Z]([A-Z]|[a-z]|[a-z0-9])*)'
OUTPUT_DIR=$(pwd)

TEMP=`getopt -o p: --long project-name: -n 'bootstrap.sh' -- "$@"`
eval set -- "$TEMP"

while true ; do
    case "$1" in
        -p|--project-name)
            case "$2" in
                "") shift 2 ;;
                *) PROJECT_FQDN_NAME=$2 ; shift 2 ;;
            esac ;;
        --) shift ; break ;;
        *) echo "internal error!" ; exit 1 ;;
    esac
done

if [[ $PROJECT_FQDN_NAME =~ $PROJECT_NAME_REGEX ]]; then
	PROJECT_NAME=${BASH_REMATCH[3]}
	PACKAGE_NAME=${BASH_REMATCH[1]}
else
	if [[ $1 =~ $PROJECT_NAME_REGEX ]]; then
		PROJECT_NAME=${BASH_REMATCH[3]}
		PACKAGE_NAME=${BASH_REMATCH[1]}
	else
		echo "please provide a project name in the format 'org.example.Project1'"
		exit 1;
	fi
fi
PACKAGE_NAME=${PACKAGE_NAME%?}

TEMP_DIR=$(mktemp -d);

echo "downloading gradle wrapper to '${TEMP_DIR}'"

GRADLE_WRAPPER_FILES=(gradlew gradle/wrapper/gradle-wrapper.jar gradle/wrapper/gradle-wrapper.properties)

for FILE in "${GRADLE_WRAPPER_FILES[@]}"
do
	DIRECTORY="${TEMP_DIR}/$(dirname $FILE)"
	
	if [ -d "${DIRECTORY}" ]; then
		mkdir -p "${DIRECTORY}"
	fi
	
	wget "${GRADLE_WRAPPER_BASE}/${FILE}" -qP ${DIRECTORY}

done

GRADLE_WRAPPER="${TEMP_DIR}/gradlew"
chmod +x $GRADLE_WRAPPER 

TEMP_GRADLE_FILE=$(mktemp -p ${TEMP_DIR} --suffix .gradle);

echo "writing temporary gradle bootstrap file to '${TEMP_GRADLE_FILE}'"

cat > ${TEMP_GRADLE_FILE} <<EOF
repositories {

	ivy {
		name "releaseBuildRepository"
		url "mango-build/build/repository"
		layout "maven"
	}

	ivy {
		name "localDevelopmentRepository"
		url System.properties['user.home'] + "/.gradle/mango_development"
		layout "maven"
	}

	mavenCentral()
}

configurations {
	bootstrap
}

dependencies { 
	bootstrap group: '${BOOTSTRAP_GROUP_NAME}', name: '${BOOTSTRAP_NAME}', version: '+', transitive: false
}
    
task extractBootstrapGradle(type: Copy) {

	configurations.bootstrap.asFileTree.each {
		println "extracting \${it} to \${bootstrapTempDir}"
		from(zipTree(it))
	}
	
	into bootstrapTempDir
	
	println "###${bootstrapTempDir}/bootstrap.gradle###"
}
EOF

echo "executing temporary gradle bootstrap file '${TEMP_GRADLE_FILE}'"

GRADLE_OUTPUT=$($GRADLE_WRAPPER --build-file ${TEMP_GRADLE_FILE} extractBootstrapGradle -PbootstrapTempDir=$TEMP_DIR)

if [[ $GRADLE_OUTPUT =~ (###(.*)###) ]]; then
	GRADLE_BOOTSTRAP_FILE=${BASH_REMATCH[2]}
	echo "temporary bootstrap returned bootstrap gradle file at '${TEMP_DIR}/${GRADLE_BOOTSTRAP_FILE}'"
else
	echo "could not parse gradle output, was:"
	echo $GRADLE_OUTPUT
	exit 1;
fi

echo "starting bootstrap with: package name '$PACKAGE_NAME', project name '$PROJECT_NAME', output dir '$OUTPUT_DIR'"


$GRADLE_WRAPPER --refresh-dependencies --rerun-tasks --build-file ${TEMP_DIR}/${GRADLE_BOOTSTRAP_FILE} copyProjectTemplate -PpackageName=$PACKAGE_NAME -PprojectName=$PROJECT_NAME -PoutputDir=$OUTPUT_DIR
