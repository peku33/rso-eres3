#!/bin/bash

CLUSTER_NAME="eres3-dbcluster"

declare -a nodes=("node2.ndb" "node1.ndb" "data2.ndb" "data1.ndb" "man2.ndb" "man1.ndb")

for i in "${nodes[@]}"
do
    docker stop $i
done

for i in "${nodes[@]}"
do
    docker rm $i
done

docker network rm $CLUSTER_NAME
