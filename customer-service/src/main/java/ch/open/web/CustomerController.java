package ch.open.web;

import ch.open.customer.Customer;
import ch.open.customer.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{id}")
    public Mono<Customer> getCustomer(@PathVariable Long id) {
        return customerRepository.findById(id);
    }

    @GetMapping("/customers")
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Mono<Customer> addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
