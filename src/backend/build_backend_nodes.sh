#!/bin/bash

set -e

NET_NAME="eres3-network"
IMAGE_NAME="eres3-backend"

declare -a backend_nodes=("backend1" "backend2" "backend3")
declare -a sql_nodes=("node1.ndb" "node2.ndb" "node3.ndb")

echo "Initializing backend nodes"
for ((i=0; i < "${#backend_nodes[@]}"; ++i))
do
    docker run -d --net=${NET_NAME} --name=${backend_nodes[$i]} --hostname=${backend_nodes[$i]} --link=${sql_nodes[$i]}:node.ndb $IMAGE_NAME
done
