1. start mongodb

```bash
docker run -d --name mongo -p 27017:27017 mongo:4.1
```

2. Connect to mongodb

Get the container id:
```bash
docker ps -a
...
2ba2ac1fccbb  mongo:4.1   "docker-entrypoint.s…"   2 minutes ago     Up 2 minutes     0.0.0.0:27017->27017/tcp   mongo
... 
```

Then connect via 
 
```bash
docker exec -it 2ba2ac1fccbb /bin/bash
root@2ba2ac1fccbb:/# mongo
> show collections
```

The should be not collections.

3. start the `temperature-service`

4. start the `iot-cloud-simulator`

5. start the `temperature-dashboard`

6. trigger the `iot-cloud-simulator`

```bash
http post :8082/start
``` 

7. Inspect the streaming (`temperature-service`)

httpie sends it with `Accept` header `*/*`
```bash
http :8081/measurements -a user:password
```

explicit:
```bash
http :8081/measurements "Accept: application/json" -a user:password
```

Streaming:
```bash
http -S :8081/measurements "Accept: application/stream+json" -a user:password
```

8. Endpoints in `temperature-dashboard`

```bash
http -S :8080/measurements/feed "Accept: text/event-stream"
```

```bash
HTTP/1.1 200 OK
Content-Type: text/event-stream;charset=UTF-8
transfer-encoding: chunked

data:{"sensorName":"Zürichsee","temperature":15.87,"time":"2018-09-10T22:34:37.175"}

data:{"sensorName":"Vierwaldstättersee","temperature":12.53,"time":"2018-09-10T22:34:39.175"}
...
```

```bash
http -S :8080/reactive-template
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

Stop emitting the measurements 

```bash
http post :8082/stop
```

