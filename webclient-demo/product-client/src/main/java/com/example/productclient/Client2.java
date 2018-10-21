package com.example.productclient;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class Client2 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        for (int i = 1; i <= 3; i++) {
            client.get().uri("/product/{id}", i).retrieve().bodyToMono(Product.class);
        }

        logTime(start);
    }

    private static void logTime(Instant start) {
        log.debug("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }

}
