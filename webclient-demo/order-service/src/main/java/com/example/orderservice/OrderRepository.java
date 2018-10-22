package com.example.orderservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, Long> {
}
