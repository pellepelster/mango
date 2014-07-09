#!/bin/sh

DOCKER_CONTAINER_NAME="mango-demo"
DOCKER_IMAGE_NAME="mango-demo-docker"
SSH_USER="mango"
SSH_PASSWORD=$SSH_USER

# check if container already present
TMP=$(docker inspect ${DOCKER_CONTAINER_NAME})

if [ $? -eq 0 ]; then

    echo -n "container '${DOCKER_CONTAINER_NAME}' found, "

	TMP=$(docker inspect ${DOCKER_CONTAINER_NAME} | grep Running | grep true)
	
	if [ $? -eq 0 ]; then
		echo "already running"
	else
		echo "not running, starting..."
		TMP=$(docker start ${DOCKER_CONTAINER_NAME})
		echo "done"
	fi

else
    echo -n "container '${DOCKER_CONTAINER_NAME}' not found, creating..."
    TMP=$(docker run -d -P --name ${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE_NAME})
    echo "done"
fi

SSH_URL=$(docker port ${DOCKER_CONTAINER_NAME} 22)
SSH_URL_REGEX="(.*):(.*)"

SSH_INTERFACE=$(echo $SSH_URL | awk -F  ":" '/1/ {print $1}')
SSH_PORT=$(echo $SSH_URL | awk -F  ":" '/1/ {print $2}')

echo "ssh running at ${SSH_INTERFACE}:${SSH_PORT}"

ssh -X -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null ${SSH_USER}@${SSH_INTERFACE} -p ${SSH_PORT} firefox