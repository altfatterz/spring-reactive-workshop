package com.example.productclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

@Slf4j
public class Client6 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        Flux.range(1, 3)
                .flatMap(i -> client.get().uri("/products/{id}", i)
                        .retrieve()
                        .bodyToMono(Product.class)
                        .flatMap(product -> client.get().uri("/products/{id}/reviews", i)
                                .retrieve()
                                .bodyToMono(Review.class)))
                .blockLast();

        logTime(start);
    }

}
