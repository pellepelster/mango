#!/bin/bash

DOCKER_IMAGE_NAME="pellepelster/mango-demo"

DOCKER_CONTAINER_NAME="mango-demo"

# check if container already present
TMP=$(docker ps -a | grep ${DOCKER_CONTAINER_NAME})
CONTAINER_FOUND=$?

TMP=$(docker ps | grep ${DOCKER_CONTAINER_NAME})
CONTAINER_RUNNING=$?

if [ $CONTAINER_FOUND -eq 0 ]; then

	echo -n "container '${DOCKER_CONTAINER_NAME}' found, "

	if [ $CONTAINER_RUNNING -eq 0 ]; then
		echo "already running"
	else
		echo -n "not running, starting..."
		TMP=$(docker start ${DOCKER_CONTAINER_NAME})
		echo "done"
	fi

else
	echo -n "container '${DOCKER_CONTAINER_NAME}' not found, creating..."
	TMP=$(docker run -d -P --name ${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE_NAME})
	echo "done"
fi

#wait for container to come up
sleep 2

# find ssh port
SSH_URL=$(docker port ${DOCKER_CONTAINER_NAME} 22)
SSH_URL_REGEX="(.*):(.*)"

SSH_INTERFACE=$(echo $SSH_URL | awk -F  ":" '/1/ {print $1}')
SSH_PORT=$(echo $SSH_URL | awk -F  ":" '/1/ {print $2}')

echo "ssh running at ${SSH_INTERFACE}:${SSH_PORT}"

ssh -i ssh/id_rsa -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -Y -X root@${SSH_INTERFACE} -p ${SSH_PORT} eclipse/eclipse -data workspace