package com.example.orderservice;

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

@RestController
public class OrderRestController {

    public static final Sort BY_AMOUNT = Sort.by(Sort.Direction.DESC, "amount");

    private WebClient webClient;

    private OrderRepository orderRepository;

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

                    Mono<String> productMono = this.webClient.get().uri("/products/{id}?delay=2", productId)
                            .retrieve()
                            .bodyToMono(Product.class)
                            .map(Product::getName);

                    Mono<String> reviewMono = this.webClient.get().uri("/products/{id}/reviews?delay=1", productId)
                            .retrieve()
                            .bodyToMono(Review.class)
                            .map(Review::getReview);

                    return Mono.zip(productMono, reviewMono, (productName, review) -> {
                        Map<String, String> data = new LinkedHashMap<>();
                        data.put("product", productName);
                        data.put("review", review);
                        return data;
                    });
                });
    }

}
