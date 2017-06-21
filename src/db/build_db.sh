#!/bin/bash

set -e

NET_NAME="eres3-network"
IMAGE_NAME="eres3-dbcluster"

declare -a ndb_mgmt_nodes=("man1.ndb" "man2.ndb")
declare -a ndb_data_nodes=("data1.ndb" "data2.ndb")
declare -a sql_nodes=("node1.ndb" "node2.ndb" "node3.ndb")


echo "Initializing data nodes"
for ((i=0; i < "${#ndb_data_nodes[@]}"; ++i))
do
    docker create --net=${NET_NAME} --name=${ndb_data_nodes[$i]} --hostname=${ndb_data_nodes[$i]} $IMAGE_NAME ndbd
done

echo "Initializing mysql nodes"
for ((i=0; i < "${#sql_nodes[@]}"; ++i))
do
    docker create --net=${NET_NAME} --name=${sql_nodes[$i]} --hostname=${sql_nodes[$i]} $IMAGE_NAME mysqld
done

# create additional initalizer node
docker create --net=${NET_NAME} --name=datainit --hostname=node1.ndb --net-alias=node1.ndb $IMAGE_NAME /etc/mysql-init.sh

echo "Initializing management nodes"
for ((i=0; i < "${#ndb_mgmt_nodes[@]}"; ++i))
do
    docker create --net=${NET_NAME} --name=${ndb_mgmt_nodes[$i]} --hostname=${ndb_mgmt_nodes[$i]} $IMAGE_NAME ndb_mgmd
done


#echo "Preparing DB for further use"
echo "Starting data nodes"
for ((i=0; i < "${#ndb_data_nodes[@]}"; ++i))
do
    docker start ${ndb_data_nodes[$i]}
done

echo "Starting mysql nodes"
for ((i=1; i < "${#sql_nodes[@]}"; ++i))
do
    docker start ${sql_nodes[$i]}
done

docker start datainit

echo "Starting management nodes"
for ((i=0; i < "${#ndb_mgmt_nodes[@]}"; ++i))
do
    docker start ${ndb_mgmt_nodes[$i]}
done

# attach to datainit - when finished it will quit automatically
docker attach datainit
docker rm datainit

# restart sql nodes to apply new changes
docker start node1.ndb
for ((i=1; i < "${#sql_nodes[@]}"; ++i))
do
    docker restart ${sql_nodes[$i]}
done
