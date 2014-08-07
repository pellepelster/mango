#!/bin/bash

OUTPUT_DIR="."
PROJECT_FQDN_NAME=
PROJECT_NAME=
PACKAGE_NAME=

PROJECT_NAME_REGEX='(([a-z][a-z0-9]*\.)*)([A-Z]([A-Z]|[a-z]|[a-z0-9])*)'

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
		echo "please provide a project name in the format 'org.example.path.ProjectName'"
		exit 1;
	fi
fi
PACKAGE_NAME=${PACKAGE_NAME%?}

TEMP_DIR=$(mktemp -d);
TEMP_GRALDE_FILE=$(mktemp -p ${TEMP_DIR} --suffix .gradle);

echo "writing temporary gradle bootstrap file to '${TEMP_GRALDE_FILE}'"

cat > ${TEMP_GRALDE_FILE} <<EOF
repositories {

	ivy {
		name "releaseBuildRepository"
		url "repository"
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
	bootstrap group: 'io.pelle.mango', name: 'mango-bootstrap', version: '+', transitive: false
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

echo "executing temporary gradle bootstrap file '${TEMP_GRALDE_FILE}'"

GRADLE_OUTPUT=$(gradle --build-file ${TEMP_GRALDE_FILE} extractBootstrapGradle -PbootstrapTempDir=$TEMP_DIR)

if [[ $GRADLE_OUTPUT =~ (###(.*)###) ]]; then
	GRADLE_BOOTSTRAP_FILE=${BASH_REMATCH[2]}
	echo "temporary bootstrap returned bootstrap gradle file at '${TEMP_DIR}/${GRADLE_BOOTSTRAP_FILE}'"
else
	echo "could not parse gradle output, was:"
	echo $GRADLE_OUTPUT
	exit 1;
fi

echo "starting bootstrap with: package name '$PACKAGE_NAME', project name '$PROJECT_NAME', output dir '$OUTPUT_DIR'"

OUTPUT_DIR=$(pwd)

gradle --refresh-dependencies --rerun-tasks --build-file ${TEMP_DIR}/${GRADLE_BOOTSTRAP_FILE} copyProjectTemplate -PpackageName=$PACKAGE_NAME -PprojectName=$PROJECT_NAME -PoutputDir=$OUTPUT_DIR
