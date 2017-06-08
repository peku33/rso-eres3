#!/bin/bash

declare -a nodes=("frontend3" "frontend2" "frontend1")

for i in "${nodes[@]}"
do
    docker stop $i
done

for i in "${nodes[@]}"
do
    docker rm $i
done
