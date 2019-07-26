package com.example.productclient;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.function.Function;

import static com.example.productclient.LogUtil.logTime;

@Slf4j
public class Client5 {

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");

    public static void main(String[] args) {

        Instant start = Instant.now();

        Flux.range(1, 3)
                .flatMap(i -> client.get().uri("/products/{id}", i)

                        // returns a `Mono<ClientResponse>` which provides access to the response and status
                        .exchange()

                        // next we transform the item emitted by this Mono asynchronously, returning the value emitted by another Mono
                        .flatMap(response -> {
                            HttpStatus status = response.statusCode();
                            HttpHeaders headers = response.headers().asHttpHeaders();
                            log.debug("Got status=" + status + ", headers=" + headers);
                            return response.bodyToMono(Product.class);
                        }))
                .blockLast();

        logTime(start);
    }

}
