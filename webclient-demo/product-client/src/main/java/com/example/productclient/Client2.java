package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

public class Client2 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        for (int i = 1; i <= 3; i++) {
            client.get().uri("/product/{id}", i).retrieve().bodyToMono(Product.class);
        }

        logTime(start);
    }

}
