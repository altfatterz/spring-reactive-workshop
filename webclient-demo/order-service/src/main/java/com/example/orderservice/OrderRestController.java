package com.example.orderservice;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@RestController
public class OrderRestController {

    public static final Sort BY_AMOUNT = Sort.by(Sort.Direction.DESC, "amount");

    private WebClient webClient;

    private OrderRepository orderRepository;

    // Spring Boot creates and pre-configures a WebClient.Builder for you, see WebClientAutoConfiguration
    // each injection point creates a newly cloned instance of the builder.

    public OrderRestController(WebClient.Builder builder, OrderRepository orderRepository) {
        this.webClient = builder.baseUrl("http://localhost:8081").build();
        this.orderRepository = orderRepository;
    }

    @GetMapping("/products/{id}")
    Mono<Product> getProduct(@PathVariable Long id) {
        return this.webClient.get().uri("/products/{id}?delay=2", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @GetMapping("/products")
    Flux<Product> getProducts() {
        return this.webClient.get().uri("/products?delay=2")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Product> getProductStream() {
        return this.webClient.get().uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @GetMapping("/orders")
    Flux<Map<String, String>> getTopOrders() {
        return orderRepository.findAll(BY_AMOUNT)
                .take(3)
                .flatMapSequential(order -> {
                    Long productId = order.getProductId();

                    Mono<Product> product = webClient.get().uri("/products/{id}?delay=2", productId)
                            .retrieve()
                            .bodyToMono(Product.class);

                    Flux<Review> reviews = product.flatMapMany(p -> webClient.get().uri("/products/{id}?delay=2", productId)
                            .retrieve().bodyToFlux(Review.class));

                    // todo

                    return Flux.combineLatest(product, reviews, new BiFunction<Product, Review, Map<String, String>>() {
                        @Override
                        public Map<String, String> apply(Product product, Review review) {
                            return null;
                        }
                    });

                });

    }

}
