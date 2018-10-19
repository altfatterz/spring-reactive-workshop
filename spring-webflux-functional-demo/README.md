#### Spring WebFlux Functional Endpoint Demo

Demonstrates:

1. `RouterFunction`
2. `HandlerFunction`
3. `RequestPredicate`
4. `HandlerFilterFunction`

```java
@Configuration
public class DemoConfiguration {

    private SecurityManager securityManager = new RandomSecurityManager();

    @Bean
    public CustomerHandler petHandler(CustomerRepository repository) {
        return new CustomerHandler(repository);
    }

    @Bean
    public RouterFunction<ServerResponse> customerRoutes(CustomerHandler customerHandler) {
        RouterFunction<ServerResponse> html = route()
                .GET("/customers/{id}", accept(TEXT_HTML), customerHandler::renderCustomer)
                .GET("/customers", accept(TEXT_HTML), customerHandler::renderCustomers)
                .build();

        RouterFunction<ServerResponse> json = route()
                .GET("/customers/{id}", accept(APPLICATION_JSON), customerHandler::getCustomer)
                .GET("/customers", accept(APPLICATION_JSON), customerHandler::getCustomers)
                .build();

        return html.and(json).filter(this::security);
    }

    public Mono<ServerResponse> security(ServerRequest request, HandlerFunction<ServerResponse> next) {
        if (this.securityManager.hasAccess(request)) {
            return next.handle(request);
        }
        else {
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
        }
    }
}

```


```java
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
```