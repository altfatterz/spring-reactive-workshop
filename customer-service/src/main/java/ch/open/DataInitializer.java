package ch.open;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
class DataInitializer implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        log.info("init...");
        customerRepository
                .deleteAll()
                .thenMany(
                        Flux.just(new Customer("Walter", "White"),
                                new Customer("Jesse", "Pinkman"),
                                new Customer("Gus", "Fring"),
                                new Customer("Saul", "Goodman"),
                                new Customer("Skyler", "White"),
                                new Customer("Hank", "Shrader"),
                                new Customer("Mike", "Ehrmantraut"))
                                .flatMap(customer -> customerRepository.save(customer)))
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done...")
                );
    }
}