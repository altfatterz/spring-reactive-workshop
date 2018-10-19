package com.example.demo;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
public class SpringWebfluxFunctionalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxFunctionalDemoApplication.class, args);
    }

    @Autowired
    private CustomerRepository repository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            repository.deleteAll()
                    .thenMany(Flux.just(new Customer("Walter", "White"),
                            new Customer("Jesse", "Pinkman"),
                            new Customer("Gus", "Fring"),
                            new Customer("Saul", "Goodman"),
                            new Customer("Skyler", "White"),
                            new Customer("Hank", "Shrader"),
                            new Customer("Mike", "Ehrmantraut")))
                    .flatMap(customer -> repository.save(customer))
                    .log()
                    .subscribe(null, null, () -> log.info("done"));

        };
    }

}
