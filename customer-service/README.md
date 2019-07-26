### Customer Service application

This application demonstrates the followings:

- Reactive Redis support
- Reactive Spring Security support
- Reactive Thymeleaf Support
- Spring WebFlux Functional (`HandlerFunction` and `RouterFunction`)
- Integration Testing with WebClient
- Testing using @WebFluxTest

1. Start Redis via:

```bash
docker container run --rm -d --name redis -p 6379:6379 redis:5.0.5
```

2. Make sure you can connect to it in order to view the keys:

```bash
docker exec -it redis /bin/bash
root@9676a1e76763:/data# redis-cli
127.0.0.1:6379> keys *
(empty list or set)
```

3. Start the application (`CustomerServiceApplication`) using IntelliJ or Eclipse or via command line 

```bash
mvn clean package
java -jar target/customer-service*.jar
```

4. After the application has started successfully inspect redis that customers have been inserted. 

```bash
127.0.0.1:6379> keys *
1) "customers"
127.0.0.1:6379> hgetall customers
 1) "5c15ba35-4e37-493c-bfc4-1ca63902d7db"
 2) "{\"id\":\"5c15ba35-4e37-493c-bfc4-1ca63902d7db\",\"firstName\":\"Mike\",\"lastName\":\"Ehrmantraut\"}"
 3) "085477db-83e1-476b-9149-cff8e4a0008c"
 4) "{\"id\":\"085477db-83e1-476b-9149-cff8e4a0008c\",\"firstName\":\"Gus\",\"lastName\":\"Fring\"}"
 5) "916ce17c-f156-4717-8fd2-39809c867428"
 6) "{\"id\":\"916ce17c-f156-4717-8fd2-39809c867428\",\"firstName\":\"Jesse\",\"lastName\":\"Pinkman\"}"
 7) "01556e8b-0223-40eb-9cb4-1abd06c83534"
 8) "{\"id\":\"01556e8b-0223-40eb-9cb4-1abd06c83534\",\"firstName\":\"Skyler\",\"lastName\":\"White\"}"
 9) "5dd68f60-e796-4b53-ac98-e4ff38714e7c"
10) "{\"id\":\"5dd68f60-e796-4b53-ac98-e4ff38714e7c\",\"firstName\":\"Hank\",\"lastName\":\"Shrader\"}"
11) "6156c719-761c-4392-83dd-d15a15c8f099"
12) "{\"id\":\"6156c719-761c-4392-83dd-d15a15c8f099\",\"firstName\":\"Walter\",\"lastName\":\"White\"}"
13) "3ee9c55e-8254-4c3a-b1fd-4cf0cb11104a"
14) "{\"id\":\"3ee9c55e-8254-4c3a-b1fd-4cf0cb11104a\",\"firstName\":\"Saul\",\"lastName\":\"Goodman\"}"```
```

The application is using a simple `CommandLineRunner` to insert some sample customers. 

5. Review the `DataInitializer` which is using the `CustomerRepository` to populate Redis.

6. Review the reactive `CustomerRepository` which is using the `ReactiveRedisOperations` template.

7. The application provides a Thymeleaf view `CustomersViewController` of the customers.

8. The application also provides a REST based view using `WebFlux.Fn` framework. Review how is done in the 
`CustomerHandler` and `RouterFunctionConfig`

9. The application is using Spring Security, review the `SecurityConfig`

In a browser try to access `http://localhost:8080`. 
Notice that that you are redirected to a redesigned login screen from Spring Security 5.

Try to login with `user:user` and you should get `Access Denied`.

Login with `admin:admin` and you should see the Customers View (use another browser or in incognito mode or just restart the application)

Endpoints available:

```bash
http :8080/customers -a admin:admin
http :8080/customers/{customerId} -a admin:admin
echo '{"firstName":"John", "lastName":"Doe"}' |  http post :8080/customers -a admin:admin
```


10. Testing

The `CustomerServiceTest` is using the @WebFluxTest slice testing approach to test only the web layer.
Notice that this way Redis in not needed.

The `CustomersServiceIntegrationTests` runs a full end-to-end test using an embedded Redis instance.

 