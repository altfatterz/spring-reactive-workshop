package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionCallback;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@SpringBootApplication
public class ReactiveTransactionsMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveTransactionsMongodbApplication.class, args);
    }

    @Bean
    TransactionalOperator transactionalOperator(ReactiveTransactionManager rtm) {
        return TransactionalOperator.create(rtm);
    }

    @Bean
    ReactiveTransactionManager reactiveTransactionManager(ReactiveMongoDatabaseFactory rmdf) {
        return new ReactiveMongoTransactionManager(rmdf);
    }
}

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;

    private final TransactionalOperator transactionalOperator;

    public Flux<Customer> saveAll(String... names) {

        Flux<Customer> customerFlux = Flux.just(names)
                .map(name -> new Customer(null, name))
                .flatMap(customerRepository::save)
                .doOnNext(customer -> Assert.isTrue(!StringUtils.isEmpty(customer.getName()), "the name must not be empty"));

        return this.transactionalOperator.execute(reactiveTransaction -> customerFlux);

    }

}

interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("customers")
class Customer {

    private String id;
    private String name;

}

