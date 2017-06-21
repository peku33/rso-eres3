FROM mysql/mysql-cluster:7.6
COPY mysql-cluster.cnf my.cnf mysql-init.sh /etc/
COPY eres3_db_entrypoint.sh /
ENTRYPOINT ["/eres3_db_entrypoint.sh"] 
