package ch.open.web;

import ch.open.customer.Customer;
import ch.open.customer.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{id}")
    public Mono<ResponseEntity<Customer>> getCustomer(@PathVariable String id) {
        return customerRepository.findById(id)
                .map(customer -> ResponseEntity.ok(customer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/customers")
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Mono<ResponseEntity<Void>> addCustomer(@RequestBody Customer payload) {
        return customerRepository.save(payload).map(new Function<Customer, ResponseEntity<Void>>() {
            @Override
            public ResponseEntity<Void> apply(Customer customer) {
                return ResponseEntity.created(
                        UriComponentsBuilder.fromPath("/customers/{id}")
                                .buildAndExpand(customer.getId()).toUri()).build();

            }
        });

    }
}
