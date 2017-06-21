#!/bin/bash

set -e

if [ $# -ne 1 ]
then
    echo "Not enough arguments - provide container name"
    exit 1
fi

NET_NAME="eres3-network"
IMAGE_NAME="eres3-lb"

echo "Initializing load balancer node $1"
docker run -d --net=${NET_NAME} --name=${1} --hostname=${1} --publish=443:443 $IMAGE_NAME
