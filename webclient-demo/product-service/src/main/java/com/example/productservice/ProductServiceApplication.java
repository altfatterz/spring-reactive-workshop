package com.example.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.web.reactive.function.BodyInserters.fromServerSentEvents;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    private static final Map<Long, Map<String, Object>> PRODUCTS = new HashMap<>();
    private static final Map<Long, List<Review>> REVIEWS = new HashMap<>();

    @Bean
    public RouterFunction<?> routes() {
        return RouterFunctions.route()
                .GET("/products/{id}", request -> {
                    Long id = Long.parseLong(request.pathVariable("id"));
                    Map<String, Object> body = PRODUCTS.get(id);
                    return body != null ? ServerResponse.ok().syncBody(body) : ServerResponse.notFound().build();
                })
                .GET("/products", request -> {
                    List<Map<String, Object>> body = new ArrayList<>(PRODUCTS.values());
                    return ServerResponse.ok().syncBody(body);
                })
                .GET("/events", request -> {
                    Flux<ServerSentEvent<Map<String, Object>>> stream =
                            Flux.interval(Duration.ofSeconds(2))
                                    .map(i -> PRODUCTS.get((i % 5) + 1))
                                    .map(data -> ServerSentEvent.builder(data).build());
                    return ServerResponse.ok().body(fromServerSentEvents(stream));
                })
                .GET("/products/{id}/reviews", request -> {
                    Long id = Long.parseLong(request.pathVariable("id"));
                    List<Review> body = getReviews(id);
                    return body != null ? ServerResponse.ok().syncBody(body) : ServerResponse.notFound().build();
                })
                .filter((request, next) -> {
                    Duration delay = request.queryParam("delay")
                            .map(s -> Duration.ofSeconds(Long.parseLong(s)))
                            .orElse(Duration.ZERO);
                    return delay.isZero() ? next.handle(request) :
                            Mono.delay(delay).flatMap(aLong -> next.handle(request));
                })
                .build();
    }

    static {
        addProduct(1L, "LEGO city");
        addProduct(2L, "L.O.L. Surprise");
        addProduct(3L, "Fire TV Stick with Alexa Voice Remote");
        addProduct(4L, "Red Dead Redemption 2");
        addProduct(5L, "The Wonky Donkey");

        LocalDateTime.now();
        addReviews(1L, new Review("good", LocalDateTime.now()));
        addReviews(1L, new Review("very good", LocalDateTime.now()));
        addReviews(1L, new Review("could be better", LocalDateTime.now()));
        addReviews(1L, new Review("bad", LocalDateTime.now()));

        addReviews(2L, new Review("good", LocalDateTime.now()));
        addReviews(2L, new Review("very good", LocalDateTime.now()));
        addReviews(2L, new Review("the best", LocalDateTime.now()));

        addReviews(3L, new Review("bad", LocalDateTime.now()));
    }

    private static void addProduct(Long id, String name) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("name", name);
        PRODUCTS.put(id, map);
    }

    private static void addReviews(Long productId, Review review) {
        getReviews(productId).add(review);
    }

    private static List<Review> getReviews(Long productId) {
        return REVIEWS.computeIfAbsent(productId, key -> new ArrayList<Review>());
    }

    static class Review {

        String value;
        LocalDateTime dateTime;

        public Review() {
        }

        public Review(String value, LocalDateTime dateTime) {
            this.value = value;
            this.dateTime = dateTime;
        }

        public String getValue() {
            return value;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }

}
