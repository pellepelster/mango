#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

OPTS=$(getopt -o t:vds: --long github-token:,verbose,dry-run,signing-password -n 'parse-options' -- "$@")
 
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi
 
eval set -- "$OPTS"
 
GITHHUB_TOKEN=""
DRY_RUN="false"
SIGNING_PASSWORD=""
VERSION_QUALIFIER="FINAL"

while true; do
	case "$1" in
		-v | --verbose ) VERBOSE=true; shift ;;
		-s | --signing-password ) SIGNING_PASSWORD="$2"; shift ; shift ;;
		-d | --dry-run ) DRY_RUN="true"; shift ;;
		-h | --help )    HELP=true; shift ;;
		-t | --github-token ) GITHHUB_TOKEN="$2"; shift; shift ;;
		-- ) shift; break ;;
		* ) break ;;
	esac
done

if [ -z "$GITHHUB_TOKEN" ]; then
    echo "github token for changelog generation not set (--github-token)"
    exit 1
fi

if [ -z "$SIGNING_PASSWORD" ]; then
    echo "signing password not set (--signing-password)"
    exit 1
fi

if ! [[ $DRY_RUN ]]; then
    echo "performing a dry run"
fi

# Accepts a version string and prints it incremented by one.
# Usage: increment_version <version> [<position>] [<leftmost>]
increment_version() {
   local usage=" USAGE: $FUNCNAME [-l] [-t] <version> [<position>] [<leftmost>]
           -l : remove leading zeros
           -t : drop trailing zeros
    <version> : The version string.
   <position> : Optional. The position (starting with one) of the number 
                within <version> to increment.  If the position does not 
                exist, it will be created.  Defaults to last position.
   <leftmost> : The leftmost position that can be incremented.  If does not
                exist, position will be created.  This right-padding will
                occur even to right of <position>, unless passed the -t flag."

   # Get flags.
   local flag_remove_leading_zeros=0
   local flag_drop_trailing_zeros=0
   while [ "${1:0:1}" == "-" ]; do
      if [ "$1" == "--" ]; then shift; break
      elif [ "$1" == "-l" ]; then flag_remove_leading_zeros=1
      elif [ "$1" == "-t" ]; then flag_drop_trailing_zeros=1
      else echo -e "Invalid flag: ${1}\n$usage"; return 1; fi
      shift; done

   # Get arguments.
   if [ ${#@} -lt 1 ]; then echo "$usage"; return 1; fi
   local v="${1}"             # version string
   local targetPos=${2-last}  # target position
   local minPos=${3-${2-0}}   # minimum position

   # Split version string into array using its periods. 
   local IFSbak; IFSbak=IFS; IFS='.' # IFS restored at end of func to                     
   read -ra v <<< "$v"               #  avoid breaking other scripts.

   # Determine target position.
   if [ "${targetPos}" == "last" ]; then 
      if [ "${minPos}" == "last" ]; then minPos=0; fi
      targetPos=$((${#v[@]}>${minPos}?${#v[@]}:$minPos)); fi
   if [[ ! ${targetPos} -gt 0 ]]; then
      echo -e "Invalid position: '$targetPos'\n$usage"; return 1; fi
   (( targetPos--  )) || true # offset to match array index

   # Make sure minPosition exists.
   while [ ${#v[@]} -lt ${minPos} ]; do v+=("0"); done;

   # Increment target position.
   v[$targetPos]=`printf %0${#v[$targetPos]}d $((${v[$targetPos]}+1))`;

   # Remove leading zeros, if -l flag passed.
   if [ $flag_remove_leading_zeros == 1 ]; then
      for (( pos=0; $pos<${#v[@]}; pos++ )); do
         v[$pos]=$((${v[$pos]}*1)); done; fi

   # If targetPosition was not at end of array, reset following positions to
   #   zero (or remove them if -t flag was passed).
   if [[ ${flag_drop_trailing_zeros} -eq "1" ]]; then
        for (( p=$((${#v[@]}-1)); $p>$targetPos; p-- )); do unset v[$p]; done
   else for (( p=$((${#v[@]}-1)); $p>$targetPos; p-- )); do v[$p]=0; done; fi

   echo "${v[*]}"
   IFS=IFSbak
   return 0
}

CURRENT_VERSION=$(git tag -l | sort | tail -n1)
NEXT_VERSION=$(increment_version $CURRENT_VERSION)

echo "current version is ${CURRENT_VERSION}, next version will be ${NEXT_VERSION}"

$DIR/gradlew --build-file=$DIR/mango-build/build.gradle uploadArchives -PversionQualifier=$VERSION_QUALIFIER -Psigning.password=$SIGNING_PASSWORD

if ! [ $? -eq 0 ]; then
	echo "build failed, aborting release"
	exit 2
fi

echo "creating tag for current version ${CURRENT_VERSION}"
if ! [[ $DRY_RUN ]]; then
	git tag -a ${CURRENT_VERSION} -m ${CURRENT_VERSION}
fi

echo "pushing tags"
if ! [[ $DRY_RUN ]]; then
	git push origin --tags
fi

echo "creating changelog"
if ! [[ $DRY_RUN ]]; then
	github_changelog_generator
	git add CHANGELOG.md
	git commit -m "changelog for version ${CURRENT_VERSION}"
fi

echo "writing new version ${NEXT_VERSION} to gradle.properties"
if ! [[ $DRY_RUN ]]; then
	sed -i.bak  -e "s/mangoVersion.*=.*/mangoVersion = ${NEXT_VERSION}/" $DIR/mango-build/gradle.properties
	git add $DIR/mango-build/gradle.properties
	git commit -m "increment version to next version ${NEXT_VERSION}"
fi

if ! [[ $DRY_RUN ]]; then
 	git push
fi
