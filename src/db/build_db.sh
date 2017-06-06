#!/bin/bash

IMAGE_NAME="eres3-dbcluster"
IP_PREFIX="192.168.103"

declare -a ndb_mgmt_nodes=("man1.ndb" "man2.ndb")
declare -a ndb_mgmt_nodes_ip=("201" "202")
declare -a ndb_data_nodes=("data1.ndb" "data2.ndb")
declare -a ndb_data_nodes_ip=("211" "212")
declare -a sql_nodes=("node1.ndb" "node2.ndb" "node3.ndb")
declare -a sql_nodes_ip=("221" "222" "223")

echo "Initializing management nodes"

for ((i=0; i < "${#ndb_mgmt_nodes[@]}"; ++i))
do
    docker run -d --net=${NET_NAME} --ip=${IP_PREFIX}.${ndb_mgmt_nodes_ip[$i]} --name=${ndb_mgmt_nodes[$i]} --hostname=${ndb_mgmt_nodes[$i]} $IMAGE_NAME ndb_mgmd
done

echo "Initializing data nodes"
for ((i=0; i < "${#ndb_data_nodes[@]}"; ++i))
do
    docker run -d --net=${NET_NAME} --ip=${IP_PREFIX}.${ndb_data_nodes_ip[$i]} --name=${ndb_data_nodes[$i]} --hostname=${ndb_data_nodes[$i]} --link=${ndb_mgmt_nodes[0]}:${ndb_mgmt_nodes[0]} --link=${ndb_mgmt_nodes[1]}:${ndb_mgmt_nodes[1]} $IMAGE_NAME ndbd
done

echo "Preparing DB for further use"
docker run --name=datainit --hostname="data1.ndb" eres3-dbcluster /etc/mysql-init.sh
docker rm datainit

echo "Initializing mysql nodes"
for ((i=0; i < "${#sql_nodes[@]}"; ++i))
do
    docker run -d --net=${NET_NAME} --ip=${IP_PREFIX}.${sql_nodes_ip[$i]} --name=${sql_nodes[$i]} --hostname=${sql_nodes[$i]} --link=${ndb_mgmt_nodes[0]}:${ndb_mgmt_nodes[0]} --link=${ndb_mgmt_nodes[1]}:${ndb_mgmt_nodes[1]} $IMAGE_NAME mysqld
done
