#!/bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

ECLIPSE_DIR="$DIR/eclipse"
DISTRIBUTIONS_DIR="$DIR/distributions"
ECLIPSE_DISTRIBUTION_URL="http://mirror.switch.ch/eclipse/technology/epp/downloads/release/mars/R/eclipse-jee-mars-R-linux-gtk-x86_64.tar.gz"

rm -rf $DISTRIBUTIONS_DIR
mkdir -p $DISTRIBUTIONS_DIR

if [ ! -d "$ECLIPSE_DIR" ]; then
	curl $ECLIPSE_DISTRIBUTION_URL | tar -xzv -C $DIR
fi

$DIR/eclipse-builder.sh --name mango-eclipse --destination $DISTRIBUTIONS_DIR \
--platform linux \
--platform macosx \
--platform windows \
$DIR/mango-eclipse.conf

pushd $DISTRIBUTIONS_DIR
for FILE in *linux*; do tar -czf ../${FILE}.tar.gz ${FILE}; done
for FILE in *macosx*; do tar -czf ../${FILE}.tar.gz ${FILE}; done
for FILE in *win*; do zip -qr ../${FILE}.zip ${FILE}; done
popd
