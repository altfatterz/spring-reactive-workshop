package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

public class Client2a {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        // the retrieve() method you also use from to retrieve a stream of objects

        Flux<Product> products = client.get().uri("/products").retrieve().bodyToFlux(Product.class);

        // subscribe to the product Flux with a consumer
        products.subscribe(product -> System.out.println(product));

        logTime(start);

        // can you explain why we don't see the returned products? For help check the Java doc of the #subscribe method
    }


}
