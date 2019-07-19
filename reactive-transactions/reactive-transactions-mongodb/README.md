In order to run the tests and the application we need a MongoDB node with a replica set.
This is because the MongoDB transaction support needs a replica set, otherwise we get MongoClientException: `Sessions are not supported by the MongoDB cluster to which this client is connected`

1. Start MongoDB Node

```bash
$ docker container run -p 27017:27017 --name reactive-tx-mongo -d mongo:4.0.10 mongod --replSet tx-replica-set 
```

2. Connect to the running MongoDB container

```bash
$ docker container exec -it reactive-tx-mongo bash
```

3. Initialise the replica set

```bash
root@c85a0a2c8c88:/# mongo --eval "rs.initiate()"
```
 
4. Create the `customers` collection.

MongoDB is capable to create the collections automatically, but here we going to insert documents within a transaction and insert statements mixed with collection creation within a transaction cannot be combined, otherwise we get
OperationNotSupportedInTransaction: `Cannot create namespace test.customers in multi-document transaction.`

```bash
root@c85a0a2c8c88:/# mongo test --eval "db.createCollection('customers')"
```

5. Connect to the Mongo instance:

```bash
root@c85a0a2c8c88:/# mongo
tx-replica-set:PRIMARY>
``` 

You should get the `tx-replica-set:PRIMARY` prompt. 

6. Review and remove the `@Ignore` tests in the `CustomerServiceTest` and build the project

TODO: improve the tests to use: https://www.testcontainers.org/

```bash
$ mvn clean package
```

7. Start the application from IntelliJ or from command line.

8. Review the `/v*/customers` endpoints which are calling different `saveAllv*` service methods showing the different ways to handle transactions.  

We included an error scenario when sending an empty `names` query parameter value. Verify that in this case the transaction is rolled back and there are no records in the database

using [httpie](https://httpie.org/)

```bash
http post :8080/v1/customers\?names=John\&names=\&names=Billy
```

or curl

```bash
curl -X POST http://localhost:8080/v1/customers\?names=John\&names=\&names=Billy
```

Check the database:

```bash
$ http post :8080/v3/customers\?names=John\&names=Jane
root@c85a0a2c8c88:/# mongo
tx-replica-set:PRIMARY> db.customers.count()
0
```

Check also for the other endpoints `/v2/customers`, `/v3/customers`, and `/v4/customers`

Notice that in `/v4/customers` we don't use a transaction. What is expected in this case?

9. Cleanup MongoDB

```bash
$ docker container exec -it reactive-tx-mongo bash
root@c85a0a2c8c88:/# mongo
tx-replica-set:PRIMARY> db.customers.deleteMany({})
```

