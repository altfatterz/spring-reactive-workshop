### webclient-demo

The `webclient-demo` demonstrates using the `WebClient` which is a reactive, non-blocking which can be used to call REST services.
Compared to `RestTemplate` has a fluent functional API with declarative composition.

Internally `WebClient` delegates to a HTTP client library. By default is using [Reactor Netty](https://github.com/reactor/reactor-netty)

The examples is composed of following modules:

1. `product-service` - is a dummy slow service which we will call with `WebClient`
2. `product-client` - is a standalone client calling the `product-service` using `RestTemplate` and `WebClient`
3. `order-service` - is a Tomcat service but uses `WebClient` to call the `product-service`

### product-service

The `product-service` has the following endpoints related to a `product`:

```bash
GET /products
GET /products/{productId}
GET /products/{productId}/reviews
GET /events
```

Via the `delay` parameter we can introduce that a delay for testing for the product related endpoints
 
For example the following service will return the product with id 1 in at least 2 seconds.

```bash
time http :8081/products/1\?delay=2

HTTP/1.1 200 OK
Content-Length: 27
Content-Type: application/json;charset=UTF-8

{
    "id": 1,
    "name": "LEGO city"    
}

http :8081/products/1\?delay=2  0.24s user 0.05s system 11% cpu 2.440 total
```

Another example retrieving the reviews related to product with 2

```bash
time http :8081/products/2/reviews\?delay=3

HTTP/1.1 200 OK
Content-Length: 42
Content-Type: application/json;charset=UTF-8

{
    "productId": 2,
    "review": "could be better"
}

http :8081/products/2/reviews\?delay=3  0.26s user 0.07s system 9% cpu 3.502 total
```

There is also a SSE (Server-Sent Events) endpoint:

```bash
http -S :8081/events

HTTP/1.1 200 OK
Content-Type: text/event-stream;charset=UTF-8
transfer-encoding: chunked

data:{"id":1,"name":"LEGO city"}

data:{"id":2,"name":"L.O.L. Surprise"}
...
```

### product-client
 
 
### order-service
 
Demonstrates the use of `WebTestClient` - which is a `WebClient` designed to be used in tests. 
 
 
 
 