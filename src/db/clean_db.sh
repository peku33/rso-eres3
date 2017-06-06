#!/bin/bash

NETWORK_NAME="eres3-network"

declare -a nodes=("node3.ndb" "node2.ndb" "node1.ndb" "data2.ndb" "data1.ndb" "man2.ndb" "man1.ndb")

for i in "${nodes[@]}"
do
    docker stop $i
done

for i in "${nodes[@]}"
do
    docker rm $i
done
