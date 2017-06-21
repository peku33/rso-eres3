#!/bin/bash

NETWORK_NAME="eres3-network"

declare -a nodes=("backend3" "backend2" "backend1")

for i in "${nodes[@]}"
do
    docker stop $i
done

for i in "${nodes[@]}"
do
    docker rm $i
done
