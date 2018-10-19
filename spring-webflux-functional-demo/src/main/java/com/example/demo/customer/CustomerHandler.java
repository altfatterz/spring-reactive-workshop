package com.example.demo.customer;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RenderingResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class CustomerHandler {

    private final CustomerRepository repository;

    public CustomerHandler(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customers = repository.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customers, Customer.class);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(customer -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(customer)))
                .switchIfEmpty(Mono.defer(() -> ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> renderCustomers(ServerRequest request) {
        Flux<Customer> customers = repository.findAll();
        return RenderingResponse.create("customers").modelAttribute("customers", customers).build()
                .map(r -> r);
    }

    public Mono<ServerResponse> renderCustomer(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(customer -> RenderingResponse.create("customer")
                        .modelAttribute("customer", customer).build());
    }


}
