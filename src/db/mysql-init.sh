#!/bin/bash
BACKEND_USER="dbuser"
BACKEND_PASS="dbpass"
BACKEND_DB="test"

mysqld --user=mysql --initialize-insecure --ndbcluster

mysqld --user=mysql --ndbcluster &
#TODO hack - implement busy waiting
sleep 5

# Provide inital values for our db
mysql --host=localhost --protocol=TCP -uroot -e "CREATE USER IF NOT EXISTS '$BACKEND_USER' IDENTIFIED BY '$BACKEND_PASS';"
mysql --host=localhost --protocol=TCP -uroot -e "CREATE DATABASE IF NOT EXISTS $BACKEND_DB;"

# HACK this should enable everyone to connect to database. On prod restrict IPs to backends only
mysql --host=localhost --protocol=TCP -uroot -e "GRANT ALL PRIVILEGES ON *.* TO '$BACKEND_USER'@'%' WITH GRANT OPTION;"

echo "Database $BACKEND_DB ready for user $BACKEND_USER "
