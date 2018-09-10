Demonstrating:

- Reactive Redis
- Reactive Spring Security
- Reactive Thymeleaf Support
- Spring WebFlux (HandlerFunction and RouterFunction)
- Testing with WebClient

Start Redis via:

```bash
docker run -d --name redis -p 6379:6379 redis:4

Unable to find image 'redis:4' locally
4: Pulling from library/redis
Digest: sha256:b77926b30ca2f126431e4c2055efcf2891ebd4b4c4a86a53cf85ec3d4c98a4c9
Status: Downloaded newer image for redis:4
4bd76953ea24a30eaa87e8a438a1330126974eaff115d2722a728c68403a07be
```

```bash
docker ps -a

CONTAINER ID  IMAGE    COMMAND                 CREATED             STATUS              PORTS                   NAMES
4bd76953ea24  redis:4  "docker-entrypoint.s…"  About a minute ago  Up About a minute   0.0.0.0:6379->6379/tcp  elated_ptolemy
```

Connect to it and get a shell via `docker exec -it <container-id> /bin/bash`

```bash

docker exec -it 4bd76953ea24 /bin/bash
root@4bd76953ea24:/data#
```

Connect to redis via `redis-cli`

```bash
root@4bd76953ea24:/data# redis-cli
127.0.0.1:6379>
```

Check that there are no keys currently

```bash
127.0.0.1:6379> keys *
(empty list or set)
```

Start the application and check that some customers were inserted into the `customers` hash

```bash
127.0.0.1:6379> keys *
1) "customers"
127.0.0.1:6379> hgetall customers
 1) "5c15ba35-4e37-493c-bfc4-1ca63902d7db"
 2) "{\"id\":\"5c15ba35-4e37-493c-bfc4-1ca63902d7db\",\"firstName\":\"Mike\",\"lastName\":\"Ehrmantraut\"}"
 3) "085477db-83e1-476b-9149-cff8e4a0008c"
 4) "{\"id\":\"085477db-83e1-476b-9149-cff8e4a0008c\",\"firstName\":\"Gus\",\"lastName\":\"Fring\"}"
 5) "916ce17c-f156-4717-8fd2-39809c867428"
 6) "{\"id\":\"916ce17c-f156-4717-8fd2-39809c867428\",\"firstName\":\"Jesse\",\"lastName\":\"Pinkman\"}"
 7) "01556e8b-0223-40eb-9cb4-1abd06c83534"
 8) "{\"id\":\"01556e8b-0223-40eb-9cb4-1abd06c83534\",\"firstName\":\"Skyler\",\"lastName\":\"White\"}"
 9) "5dd68f60-e796-4b53-ac98-e4ff38714e7c"
10) "{\"id\":\"5dd68f60-e796-4b53-ac98-e4ff38714e7c\",\"firstName\":\"Hank\",\"lastName\":\"Shrader\"}"
11) "6156c719-761c-4392-83dd-d15a15c8f099"
12) "{\"id\":\"6156c719-761c-4392-83dd-d15a15c8f099\",\"firstName\":\"Walter\",\"lastName\":\"White\"}"
13) "3ee9c55e-8254-4c3a-b1fd-4cf0cb11104a"
14) "{\"id\":\"3ee9c55e-8254-4c3a-b1fd-4cf0cb11104a\",\"firstName\":\"Saul\",\"lastName\":\"Goodman\"}"```
```

Access the `localhost:8080/` in your browser.
Notice that that you are redirected to a login screen from spring security.

Login with `user:user` and you should get `Access Denied`.

Login with `admin:admin` and you should see the Customers View (use another browser or in incognito mode)