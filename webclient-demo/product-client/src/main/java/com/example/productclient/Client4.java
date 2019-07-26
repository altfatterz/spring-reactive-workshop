package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

public class Client4 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        // with 'flatMap()' we subscribe implicitly to the internal Mono<Product> publishers, flattening them into a single Flux
        // returned elements can interleave
        Flux<Product> products = Flux.range(1, 3)
                .flatMap(i -> client.get().uri("/products/{id}", i).retrieve().bodyToMono(Product.class));

        // we subscribe to this Flux and block indefinitely until the upstream signals its last value or completes.
        products.blockLast();

        logTime(start);
    }

}
