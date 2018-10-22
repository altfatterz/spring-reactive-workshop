package com.example.orderservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(OrderRepository repository) {
        return args -> {
            Flux<Order> orders = Flux.just(
                    new Order(1L, 10.20, 1L),
                    new Order(2L, 51.5, 2L),
                    new Order(3L, 16.0, 3L),
                    new Order(4L, 611.80, 4L),
                    new Order(5L, 100.00, 5L));

            repository.deleteAll()
                    .thenMany(repository.saveAll(orders))
                    .blockLast();
        };
    }
}
