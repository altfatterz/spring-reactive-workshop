1. start mongodb

2. start temperature-service

3. start iot-cloud-simulator

4. trigger the ito-cloud-simulator:

```bash
http post :8082/start
``` 

5. Inspect the streaming (`temperature-service`)

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

6. Endpoints in `temperature-dashboard`

```
http://localhost:8080/foo
http://localhost:8080/events
http://localhost:8080/events2
```


