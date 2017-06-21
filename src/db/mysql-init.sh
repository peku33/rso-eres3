#!/bin/bash
BACKEND_USER="dbuser"
BACKEND_PASS="dbpass"
BACKEND_DB="test"
MYSQL="mysql --host=localhost --protocol=TCP -uroot"

mysqld --user=mysql --initialize-insecure --ndbcluster

mysqld --user=mysql --ndbcluster &
#TODO hack - implement busy waiting
sleep 5

# Provide inital values for our db
$MYSQL -e "CREATE USER IF NOT EXISTS '$BACKEND_USER' IDENTIFIED BY '$BACKEND_PASS';"; echo $?
$MYSQL -e "CREATE DATABASE IF NOT EXISTS $BACKEND_DB;"; echo $?

# HACK this should enable everyone to connect to database. On prod restrict IPs to backends only
$MYSQL -e "GRANT ALL PRIVILEGES ON *.* TO '$BACKEND_USER'@'%' WITH GRANT OPTION;"; echo $?

# Distribute privileges to ndb clusters
$MYSQL < /usr/share/mysql/ndb_dist_priv.sql
$MYSQL -e "CALL mysql.mysql_cluster_move_privileges();" 
$MYSQL -e "SELECT CONCAT('Conversion ', IF(mysql.mysql_cluster_privileges_are_distributed(), 'succeeded', 'failed'), '.') AS Result;"

echo "Database $BACKEND_DB ready for user $BACKEND_USER "
