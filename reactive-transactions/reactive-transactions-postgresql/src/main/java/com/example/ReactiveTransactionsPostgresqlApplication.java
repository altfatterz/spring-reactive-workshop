package com.example;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableTransactionManagement
public class ReactiveTransactionsPostgresqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveTransactionsPostgresqlApplication.class, args);
    }

    @Bean
    ConnectionFactory postgresConnectionFactory(@Value("${spring.r2dbc.url}") String url) {
        return ConnectionFactories.get(url);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory cf) {
        return new R2dbcTransactionManager(cf);
    }

}

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;

    private final TransactionalOperator transactionalOperator;

    public Flux<Customer> saveAllv1(String... names) {

        Flux<Customer> customers = Flux.just(names)
                .map(name -> new Customer(null, name))
                .flatMap(customerRepository::save)
                // doOnNext is for side-effects: want to react (eg. log) to item emissions in an intermediate step of your stream, but we still want still want the value to propagate down the stream.
                // onNext is more final, it consumes the value
                .doOnNext(customer -> Assert.isTrue(!StringUtils.isEmpty(customer.getName()), "'names' query parameter must not be empty"));

        return this.transactionalOperator.execute(reactiveTransaction -> customers);
    }

    public Flux<Customer> saveAllv2(String... names) {

        Flux<Customer> customers = Flux.just(names)
                .map(name -> new Customer(null, name))
                .flatMap(customerRepository::save)
                .doOnNext(customer -> Assert.isTrue(!StringUtils.isEmpty(customer.getName()), "'names' query parameter must not be empty"))
                .as(this.transactionalOperator::transactional);

        return customers;
    }

    @Transactional
    public Flux<Customer> saveAllv3(String... names) {
        Flux<Customer> customers = Flux.just(names)
                .map(name -> new Customer(null, name))
                .flatMap(customerRepository::save)
                .doOnNext(customer -> Assert.isTrue(!StringUtils.isEmpty(customer.getName()), "'names' query parameter must not be empty"));

        return customers;
    }

    public Flux<Customer> saveAllv4(String... names) {
        Flux<Customer> customers = Flux.just(names)
                .map(name -> new Customer(null, name))
                .flatMap(customerRepository::save)
                .doOnNext(customer -> Assert.isTrue(!StringUtils.isEmpty(customer.getName()), "'names' query parameter must not be empty"));


        return customers;

    }
}

interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("customers")
class Customer {

    @Id
    private Long id;

    private String name;

}