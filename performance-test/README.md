Build the project with `mvn clean package`

1. Start up `slow-service` (port: 9090)
2. Start up `synchronous-client` (port: 9091) uses max 10 threads
3. Start up `reactive-client` (port: 9092) uses 8 threads (Schedulers#parallel())

4. Start `load-generator` with 10 concurrent clients for the `sychronous-client`:

```
java -jar target/load-generator.jar 10 9091

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 5209
-----------------------------------------
ms     %     Task name
-----------------------------------------
05209  100%  concurrent access: 10
```

5. Start `load-generator` with 20 concurrent clients for the `synchronous-client`:

```
java -jar target/load-generator.jar 20 9091

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 10195
-----------------------------------------
ms     %     Task name
-----------------------------------------
10195  100%  concurrent access: 20
```

6. Start `load-generator` with 20 concurrent clients for the `reactive-client`:

```
java -jar target/load-generator.jar 20 9092

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 5215
-----------------------------------------
ms     %     Task name
-----------------------------------------
05215  100%  concurrent access: 20
```

7. Start `load-generator` with 100 concurrent clients for the `reactive-client`:

```
java -jar target/load-generator.jar 100 9092

ch.open.loadgenerator.LoadGenerator      : StopWatch '': running time (millis) = 5245
-----------------------------------------
ms     %     Task name
-----------------------------------------
05245  100%  concurrent access: 100
```

8. Inspect the load using `jvisualvm`