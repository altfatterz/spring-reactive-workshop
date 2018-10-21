### Spring Reactive Workshop

#### Clone and build

```bash
git clone https://github.com/altfatterz/spring-reactive-workshop
cd spring-reactive-workshop
mvn clean install
```

Expected result:

```text
[INFO] temperature-service 0.0.1-SNAPSHOT ................. SUCCESS [ 17.029 s]
[INFO] iot-cloud-simulator 0.0.1-SNAPSHOT ................. SUCCESS [  1.317 s]
[INFO] temperature-dashboard 0.0.1-SNAPSHOT ............... SUCCESS [  5.073 s]
[INFO] current-weather .................................... SUCCESS [  0.143 s]
[INFO] load-generator 0.0.1-SNAPSHOT ...................... SUCCESS [  0.470 s]
[INFO] reactive-client 0.0.1-SNAPSHOT ..................... SUCCESS [  0.475 s]
[INFO] reactive-client-webflux 0.0.1-SNAPSHOT ............. SUCCESS [  0.444 s]
[INFO] slow-service 0.0.1-SNAPSHOT ........................ SUCCESS [  0.418 s]
[INFO] synchronous-client 0.0.1-SNAPSHOT .................. SUCCESS [  0.382 s]
[INFO] performance-test ................................... SUCCESS [  0.008 s]
[INFO] customer-service 0.0.1-SNAPSHOT .................... SUCCESS [  8.885 s]
[INFO] async-producer 0.0.1-SNAPSHOT ...................... SUCCESS [  3.412 s]
[INFO] async-consumer 0.0.1-SNAPSHOT ...................... SUCCESS [  3.241 s]
[INFO] reactive-producer 0.0.1-SNAPSHOT ................... SUCCESS [  2.919 s]
[INFO] reactive-consumer 0.0.1-SNAPSHOT ................... SUCCESS [  2.833 s]
[INFO] rabbitmq-demo ...................................... SUCCESS [  0.010 s]
[INFO] spring-webflux-functional-demo 0.0.1-SNAPSHOT ...... SUCCESS [  2.112 s]
[INFO] reactor-core-workshop-solution ..................... SUCCESS [ 15.088 s]
[INFO] reactor-core-workshop .............................. FAILURE [  2.057 s]
[INFO] spring-reactive-workshop 1.0-SNAPSHOT .............. SKIPPED
[INFO] ------------------------------------------------------------------------
```

The `reactore-core-workshop` tests are expected to fail. Your task is to make them pass :)

When importing to IntelliJ or Eclipse make sure you have the `Lombok Plugin` installed.
