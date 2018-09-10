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

```
http://localhost:8080/foo
http://localhost:8080/events
http://localhost:8080/events2
```

Dashboard consuming it from:

```bash
http -S :8080/measurements/feed "Accept: text/event-stream"
```
