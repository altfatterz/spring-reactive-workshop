package com.example.productclient;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.time.Instant;


@Slf4j
public class Client1 {

    private static RestTemplate restTemplate = new RestTemplate();

    static {
        String baseUrl = "http://localhost:8081?delay=2";
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    public static void main(String[] args) {

        Instant start = Instant.now();

        for (int i = 1; i <= 3; i++) {
            restTemplate.getForObject("/products/{id}", Product.class, i);
        }

        logTime(start);
    }


    private static void logTime(Instant start) {
        log.debug("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }
}
