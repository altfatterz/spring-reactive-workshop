#!/usr/bin/env bash
data=$HOME/temp/reactive-transactions-mongodb-data/
mkdir -p $data
nohup mongod --replSet tx-replica-set --dbpath $data &
mongo --eval "rs.initiate()"
mongo test --eval "db.createCollection('customers')"

echo "To stop the process use: kill <pid-of-mongod>"