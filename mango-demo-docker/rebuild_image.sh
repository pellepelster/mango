#!/bin/bash

ECLIPSE_DOWNLOAD_LINK="http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/technology/epp/downloads/release/luna/R/eclipse-standard-luna-R-linux-gtk-x86_64.tar.gz"
ECLIPSE_FILE=$(basename $ECLIPSE_DOWNLOAD_LINK)

if [ ! -f ${ECLIPSE_FILE} ]
then
  echo "downloading ${ECLIPSE_DOWNLOAD_LINK}"
  wget ${ECLIPSE_DOWNLOAD_LINK}
fi

if [ ! -d "eclipse" ]
then
tar -xzvf ${ECLIPSE_FILE}
fi

sudo docker rm -f pellepelster/mango-demo
sudo docker build -t pellepelster/mango-demo .