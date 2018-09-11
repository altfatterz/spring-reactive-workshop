### Current Weather application

The application is composed of the following 3 services: 
- `iot-cloud-simulator`
- `temperature-service`
- `temperature-dashboard`

The `iot-cloud-simulator` is emitting sensor data about lake temperatures and sends this data via HTTP POST 
to `temperature-service` for further processing. The `temperature-serice` application stores the measurements in a 
MongoDB capped collection and makes it available via a REST endpoint for the `temperature-dashboard` UI application.
The `temperature-dashboard` receives the measurements via Server-Sent Events and presents it on a graph.

Setup the application:

1. Start MongoDB

```bash
docker run -d --name mongo -p 27017:27017 mongo:4.1
```

2. Make sure you can connect to MongoDB

Then connect via:
 
```bash
docker exec -it mongo /bin/bash
root@2ba2ac1fccbb:/# mongo
> show collections
```

3. Start the `temperature-service`, `iot-cloud-simulator` and `temperature-dashboard` applications. If you imported the
project into IntelliJ use the `Run Dashboard` view or you can start via command line using `java -jar ...` 

4. Verify that the MongoDB measurements collection is created and is currently empty:

```
root@2ba2ac1fccbb:/# mongo
> show collections
measurement
> db.measurement.count();
```

5. The `iot-cloud-simulator` is not emitting the measurements after starting, we need to trigger the emission process via: 

```bash
http post :8082/start
``` 
or 
```bash
curl -i -XPOST http://localhost:8082/start
```

Check the logs of the `iot-cloud-simulator` process to view that a subscription was made to the sensor data measurements
Flux. 

```
2018-09-11 09:21:16.159  INFO 78867 --- [ctor-http-nio-4] generator  : request(32)
2018-09-11 09:21:18.166  INFO 78867 --- [     parallel-1] generator  : onNext(Measurement(sensorName=Bodensee, temperature=10.92, time=2018-09-11T09:21:18.166))
2018-09-11 09:21:20.163  INFO 78867 --- [     parallel-1] generator  : onNext(Measurement(sensorName=Vierwaldstättersee, temperature=12.40, time=2018-09-11T09:21:20.163))
2018-09-11 09:21:22.166  INFO 78867 --- [     parallel-1] generator  : onNext(Measurement(sensorName=Bodensee, temperature=10.867, time=2018-09-11T09:21:22.165))
2018-09-11 09:21:24.163  INFO 78867 --- [     parallel-1] generator  : onNext(Measurement(sensorName=Vierwaldstättersee, temperature=12.60, time=2018-09-11T09:21:24.163))
2018-09-11 09:21:26.165  INFO 78867 --- [     parallel-1] generator  : onNext(Measurement(sensorName=Bodensee, temperature=11.25, time=2018-09-11T09:21:26.165))
...
```

6. This measurement data is streamed into `temperature-services`. Check how is done using `Flux<Measurement>` as `@RequestBody`
Checking the logs of `temperature-service` you will see the `ingest` stream

```
2018-09-11 09:21:16.211  INFO 78860 --- [     parallel-3] ingest  : | request(unbounded)
2018-09-11 09:21:18.240  INFO 78860 --- [ntLoopGroup-2-2] ingest  : | onNext(Measurement(id=5b976ceeb14121340cf5e656, sensorName=Bodensee, temperature=10.92, time=2018-09-11T09:21:18.166))
2018-09-11 09:21:20.169  INFO 78860 --- [ntLoopGroup-2-2] ingest  : | onNext(Measurement(id=5b976cf0b14121340cf5e657, sensorName=Vierwaldstättersee, temperature=12.4, time=2018-09-11T09:21:20.163))
2018-09-11 09:21:22.171  INFO 78860 --- [ntLoopGroup-2-2] ingest  : | onNext(Measurement(id=5b976cf2b14121340cf5e658, sensorName=Bodensee, temperature=10.867, time=2018-09-11T09:21:22.165))
2018-09-11 09:21:24.167  INFO 78860 --- [ntLoopGroup-2-2] ingest  : | onNext(Measurement(id=5b976cf4b14121340cf5e659, sensorName=Vierwaldstättersee, temperature=12.6, time=2018-09-11T09:21:24.163))
...
```

6. Verify that the size of the MongoDB `measurements` capped collection is increasing:

```bash
> db.measurement.count();
13
> db.measurement.count();
14
...
```

7. Inspect the streaming from the `temperature-service`

```bash
http -S :8081/measurements "Accept: application/stream+json" -a user:password
```
or 
```bash
curl -i http://localhost:8081/measurements -H "Accept: application/stream+json" -u user:password
```

Notice, that with this request you subscribed to the stream, check the logs of the `temperature-service`

```
2018-09-11 09:40:43.932  INFO 80029 --- [     parallel-3] egress-1                                 : onSubscribe(FluxOnErrorResume.ResumeSubscriber)
2018-09-11 09:40:43.932  INFO 80029 --- [     parallel-3] egress-1                                 : request(1)
2018-09-11 09:40:44.191  INFO 80029 --- [ntLoopGroup-2-3] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:5, serverValue:46}] to localhost:27017
2018-09-11 09:40:48.171  INFO 80029 --- [ntLoopGroup-2-3] egress-1                                 : onNext(Measurement(id=5b977180b14121389d36a25f, sensorName=Zürichsee, temperature=15.95, time=2018-09-11T09:40:48.166))
```

If you cancel the the request you will see also that the subscription is cancelled:

```
2018-09-11 09:40:52.174  INFO 80029 --- [ctor-http-nio-3] egress-1                                 : cancel()
```

Notice, when getting the measurements if you omit or send `application/json` as accept header that you will get 
back a JSON array list and not a streamed response.


8. Access the UI on `http://localhost:8080` in a browser. As you will see the the measurements are presented using a graph. 
The UI is getting data via Server-Sent Event from the following endpoint. Check the data received in the Developer Console or 
you can also access it via command line: 

```bash
http -S :8080/measurements/feed "Accept: text/event-stream"
```
or
```bash
curl -i http://localhost:8080/measurements/feed -H "Accept: text/event-stream"
```

Check the logs of the `temperature-dashboard` and `temperature-service`. Notice that for each `feed-*` stream there is 
`egress-*` stream.  

9. The `temperature-dashboard` has another endpoint `http://localhost:8080/reactive-template` which is using different 
approach. It is using the `IReactiveDataDriverContextVariable` which is a wrapper that avoids Spring WebFlux to resolve 
before rendering the HTML. It sets Thymeleaf in data-driven mode in order to produce (render) Server-Sent Events as 
the Flux produces values. It creates a new lazy context variable, wrapping a reactive asynchronous data stream and 
specifying a buffer size.

```bash
http -S :8080/reactive-template
```

or 

```bash
curl -i http://localhost:8080/reactive-template
```

```
HTTP/1.1 200 OK
Content-Language: en-CH
Content-Type: text/html
transfer-encoding: chunked

<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Temperature Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>

</head>
<body>

<div class="container wrapper">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Measurements</th>
        </tr>
        </thead>
        <tr>
            <td>Measurement(sensorName=Bodensee, temperature=11.14, time=2018-09-10T22:37:03.176)</td>
            
...

```

10. The `iot-cloud-simulator` has a `/stop` endpoint. Check what happens when you stop the `ingest` Flux 


