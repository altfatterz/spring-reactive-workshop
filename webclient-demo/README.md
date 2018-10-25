The `webclient-demo` demonstrates using the `WebClient`

The examples is composed of following modules:

1. `product-service` - is a dummy slow service which we will call with `WebClient`
2. `product-client` - is a standalone client calling the `product-service` using `RestTemplate` and `WebClient`
3. `order-service` - is a Tomcat service but uses `WebClient` to call the `product-service`



The `product-service` has the following endpoints:

```bash
http :8081/products
http :8081/products/1
http :8081/products/1/reviews
```

Via the `delay` parameter we can introduce that a delay for testing. 
For example the following service will respond after 2 seconds.

```bash
http :8081/products/1\?delay=2

HTTP/1.1 200 OK
Content-Length: 27
Content-Type: application/json;charset=UTF-8

{
    "id": 1,
    "name": "LEGO city"
}
```

There is also a SSE endpoint:

```bash
http -S :8081/events

HTTP/1.1 200 OK
Content-Type: text/event-stream;charset=UTF-8
transfer-encoding: chunked

data:{"id":1,"name":"LEGO city"}

data:{"id":2,"name":"L.O.L. Surprise"}
...
```

 
 
 
 