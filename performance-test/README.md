## Performance test application

The application is composed of the followings:

- `slow-service` - provides a /slow endpoint which returns response in 5 seconds
- `synchronous-client` - running in tomcat, calls `slow-service` using `RestTemplate`
- `reactive-client` - running in tomcat, calls `slow-service` using `WebClient`
- `reactive-client-webflux` - running in netty, calls `slow-serivce` using `WebClient`
- `load-generator` - creates load on the `synchronous-client`, `reactive-client` and `reactive-client-webflux` 

1. Start up `slow-service` (port: 9090)
2. Start up `synchronous-client` (port: 9091) uses max 8 threads
3. Start up `reactive-client` (port: 9092) uses max 8 threads
4. Start up `reactive-client-webflux` (port: 9093) uses 8 threads (Schedulers#parallel())

5. Start `load-generator` with 10 concurrent clients for the `sychronous-client`:

```
java -jar target/load-generator.jar 10 9091

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 10196
-----------------------------------------
ms     %     Task name
-----------------------------------------
10196  100%  concurrent access: 10
```

6. Start `load-generator` with 20 concurrent clients for the `synchronous-client`:

```
java -jar target/load-generator.jar 20 9091

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 15262
-----------------------------------------
ms     %     Task name
-----------------------------------------
15262  100%  concurrent access: 20
```

7. Start `load-generator` with 20 concurrent clients for the `reactive-client`:

```
java -jar target/load-generator.jar 20 9092

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 5215
-----------------------------------------
ms     %     Task name
-----------------------------------------
05215  100%  concurrent access: 20
```

8. Start `load-generator` with 100 concurrent clients for the `reactive-client-webflux`:

```
java -jar target/load-generator.jar 20 9093

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 5212
-----------------------------------------
ms     %     Task name
-----------------------------------------
05212  100%  concurrent access: 20
```

Try for 100 clients as well for `reactive-client` and `reactive-client-webflux`

9. Inspect the load using `jvisualvm`