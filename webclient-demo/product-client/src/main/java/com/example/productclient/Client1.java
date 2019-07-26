package com.example.productclient;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Instant;

import static com.example.productclient.LogUtil.logTime;

public class Client1 {

    private static String baseUrl = "http://localhost:8081?delay=2";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));

        Instant start = Instant.now();

        for (int i = 1; i <= 3; i++) {
            restTemplate.getForObject("/products/{id}", Product.class, i);
        }

        // can estimate how long will it take before running it?

        logTime(start);
    }

}
