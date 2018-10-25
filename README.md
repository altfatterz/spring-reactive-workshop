### Spring Reactive Workshop

#### Clone and build

```bash
git clone https://github.com/altfatterz/spring-reactive-workshop
cd spring-reactive-workshop
mvn clean install
```

Expected result:

```text
[INFO] temperature-service 0.0.1-SNAPSHOT ................. SUCCESS [ 12.061 s]
[INFO] iot-cloud-simulator 0.0.1-SNAPSHOT ................. SUCCESS [  1.006 s]
[INFO] temperature-dashboard 0.0.1-SNAPSHOT ............... SUCCESS [  5.429 s]
[INFO] current-weather .................................... SUCCESS [  0.047 s]
[INFO] load-generator 0.0.1-SNAPSHOT ...................... SUCCESS [  0.558 s]
[INFO] reactive-client 0.0.1-SNAPSHOT ..................... SUCCESS [  0.534 s]
[INFO] reactive-client-webflux 0.0.1-SNAPSHOT ............. SUCCESS [  0.386 s]
[INFO] slow-service 0.0.1-SNAPSHOT ........................ SUCCESS [  0.371 s]
[INFO] synchronous-client 0.0.1-SNAPSHOT .................. SUCCESS [  0.296 s]
[INFO] performance-test ................................... SUCCESS [  0.002 s]
[INFO] customer-service 0.0.1-SNAPSHOT .................... SUCCESS [ 12.212 s]
[INFO] async-producer 0.0.1-SNAPSHOT ...................... SUCCESS [  3.352 s]
[INFO] async-consumer 0.0.1-SNAPSHOT ...................... SUCCESS [  2.823 s]
[INFO] reactive-producer 0.0.1-SNAPSHOT ................... SUCCESS [  2.598 s]
[INFO] reactive-consumer 0.0.1-SNAPSHOT ................... SUCCESS [  2.565 s]
[INFO] rabbitmq-demo ...................................... SUCCESS [  0.002 s]
[INFO] spring-webflux-functional-demo 0.0.1-SNAPSHOT ...... SUCCESS [  1.865 s]
[INFO] reactor-core-workshop-solution ..................... SUCCESS [ 15.045 s]
[INFO] reactor-core-workshop .............................. FAILURE [  2.020 s]
[INFO] spring-reactive-workshop 1.0-SNAPSHOT .............. SKIPPED
```

The `reactore-core-workshop` tests are expected to fail. Your task is to make them pass :)

When importing to IntelliJ or Eclipse make sure you have the `Lombok Plugin` installed.
