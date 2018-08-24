Start up mongo

```bash
mongod --dbpath ~/apps/mongodb
```



httpie sends it with `Accept` header headerAccept:
 
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


