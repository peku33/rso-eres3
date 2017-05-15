#!/bin/bash

CLUSTER_NAME="eres3-dbcluster"
IP_PREFIX="192.168.103"

docker network create $CLUSTER_NAME --subnet=$IP_PREFIX.0/24

docker run -d --net=$CLUSTER_NAME --name=man1.ndb --ip=$IP_PREFIX.201 $CLUSTER_NAME ndb_mgmd
docker run -d --net=$CLUSTER_NAME --name=man2.ndb --ip=$IP_PREFIX.202 $CLUSTER_NAME ndb_mgmd
docker run -d --net=$CLUSTER_NAME --name=data1.ndb --ip=$IP_PREFIX.211 $CLUSTER_NAME ndbd
docker run -d --net=$CLUSTER_NAME --name=data2.ndb --ip=$IP_PREFIX.212 $CLUSTER_NAME ndbd
docker run -d --net=$CLUSTER_NAME --name=node1.ndb --ip=$IP_PREFIX.221 $CLUSTER_NAME mysqld
docker run -d --net=$CLUSTER_NAME --name=node2.ndb --ip=$IP_PREFIX.222 $CLUSTER_NAME mysqld
