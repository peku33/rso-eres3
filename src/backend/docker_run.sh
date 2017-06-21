#!/bin/bash

set -e

if [ $# -ne 2 ]
then
    echo "Not enough arguments - provide container name and sql node to link to"
    exit 1
fi

NET_NAME="eres3-network"
IMAGE_NAME="eres3-backend"

echo "Initializing backend node $1 (linking to sql node $2)"
docker run -d --net=${NET_NAME} --name=${1} --hostname=${1} --link=${2}:node.ndb $IMAGE_NAME
