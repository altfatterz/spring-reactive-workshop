package com.example.productclient;

import org.springframework.web.reactive.function.client.WebClient;

public class Client7 {

    private static WebClient client = WebClient.create("http://localhost:8081");

    public static void main(String[] args) {

        client.get().uri("/events")
                .retrieve()
                .bodyToFlux(Product.class)
               // .take(4)
                .blockLast();
    }
}

