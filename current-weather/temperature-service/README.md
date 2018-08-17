Start up mongo

```bash
mongod --dbpath ~/apps/mongodb
```



httpie sends it with `Accept` header headerAccept:
 
```bash
http :8081/measurements
```

explicit:
```bash
http :8081/measurements "Accept: application/json"
```

Streaming:
```bash
http -S :8080/measurements "Accept: application/stream+json"
```
