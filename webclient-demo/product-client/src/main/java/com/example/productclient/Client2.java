package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

public class Client2 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        for (int i = 1; i <= 3; i++) {

            // the retrieve() method is the easiest way to get a response body and decode it

            Mono<Product> product = client.get().uri("/product/{id}", i).retrieve().bodyToMono(Product.class);
        }

        // can you explain why it took only around 100 ms ? Is that correct?
        logTime(start);
    }

}
