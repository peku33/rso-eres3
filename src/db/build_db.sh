#!/bin/bash

IMAGE_NAME="eres3-dbcluster"
NETWORK_NAME="eres3-network"
IP_PREFIX="192.168.103"

echo "Initializing management nodes"
docker run -d --net=$NETWORK_NAME --name=man1.ndb --ip=$IP_PREFIX.201 $IMAGE_NAME ndb_mgmd
docker run -d --net=$NETWORK_NAME --name=man2.ndb --ip=$IP_PREFIX.202 $IMAGE_NAME ndb_mgmd

echo "Initializing data nodes"
docker run -d --net=$NETWORK_NAME --name=data1.ndb --ip=$IP_PREFIX.211 $IMAGE_NAME ndbd
docker run -d --net=$NETWORK_NAME --name=data2.ndb --ip=$IP_PREFIX.212 $IMAGE_NAME ndbd

echo "Preparing DB for further use"
docker run --net=eres3-network --name=datainit --ip=192.168.103.223 eres3-dbcluster /etc/mysql-init.sh
docker rm datainit

echo "Initializing mysql nodes"
docker run -d --net=$NETWORK_NAME --name=node1.ndb --ip=$IP_PREFIX.221 $IMAGE_NAME mysqld
docker run -d --net=$NETWORK_NAME --name=node2.ndb --ip=$IP_PREFIX.222 $IMAGE_NAME mysqld
