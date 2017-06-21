#!/bin/bash

# Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; version 2 of the License.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA

set -e

ACTION="$1"
CHILD=""
shift;


# added handler for SIGTERM signal
# should forward it to the SQL call below
_term_handler()
{
    echo "Caught SIGTERM - forwarding to $CHILD"
    kill "$CHILD"
    wait "$CHILD"
}

trap _term_handler SIGTERM


echo "Launching with user arguments: $@"

if [ $ACTION == "ndb_mgmd" ]; then
    echo "Giving other containers a chance"
    sleep 2
    echo "Starting ndb_mgmd"
    ndb_mgmd -f /etc/mysql-cluster.cnf --nodaemon "$@" &

elif [ $ACTION == "ndbd" ]; then
    echo "Starting ndbd"
    ndbd --nodaemon "$@" &

elif [ $ACTION == "mysqld" ]; then
    if [ ! -d "/var/lib/mysql/mysql" ]; then
        echo "Initializing MySQL database"
        echo "Check the following output for your one-time password"
        mysqld --user=mysql --initialize
        echo "Done initializing database"
    fi
    echo "Starting mysqld"
    mysqld --user=mysql "$@" &

elif [ $ACTION == "ndb_mgm" ]; then
    echo "Starting ndb_mgm"
    ndb_mgm "$@" &
else
    set -- "$ACTION" "$@"
    echo "Running custom user command $@"
    exec "$@"
fi

CHILD=$!
wait "$CHILD"
echo "Waiting for all children"
wait
