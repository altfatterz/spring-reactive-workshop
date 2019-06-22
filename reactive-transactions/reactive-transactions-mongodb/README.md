Create a single node mongodb replica set using the `mongo-single-node-replica-set-for-tx.sh` script
The script also created a `customers` collection in the `test` database

It needs a replica set, otherwise: MongoClientException: `Sessions are not supported by the MongoDB cluster to which this client is connected`

The `CustomerService.saveAll` transaction method needs that the `customers` collection to be created upfront.
Otherwise it returns: OperationNotSupportedInTransaction: `Cannot create namespace test.customers in multi-document transaction.`

Enable the `CustomerServiceTest.saveAll()` to run the tests.

TODO: 
improve the test to use: https://www.testcontainers.org/