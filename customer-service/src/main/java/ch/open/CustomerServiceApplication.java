package ch.open;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails john = User.withUsername("user").password("{noop}user").roles("USER").build();
        UserDetails joe = User.withUsername("admin").password("{noop}admin").roles("ADMIN", "USER").build();

        return new MapReactiveUserDetailsService(john, joe);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                    .pathMatchers("/customers").access(this::isAdmin)
                    .pathMatchers("/customers-view").access(this::isAdmin)
                .anyExchange().authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();

        return http.build();
    }

    private Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication,
                                                AuthorizationContext authorizationContext) {
        return authentication
                .map(Authentication::getName)
                .map(username -> username.equals("admin"))
                .map(AuthorizationDecision::new);
    }

    @Bean
    public ReactiveRedisTemplate<String, Customer> reactiveJsonCustomerRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, Customer> serializationContext = RedisSerializationContext
                .<String, Customer>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(Customer.class))
                .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public RouterFunction route(CustomerHandler customerHandler) {
        return RouterFunctions.route(
                GET("/customers").and(accept(MediaType.APPLICATION_STREAM_JSON)), customerHandler::getCustomers);
    }

}

@Controller
class IndexController {

    @GetMapping("/")
    String index() {
        return "redirect:/customers-view";
    }

}

@Controller
class CustomersViewController {

    private final CustomerRepository customerRepository;

    public CustomersViewController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(value = "/customers-view")
    Rendering customers() {
        return Rendering.view("customers")
                .modelAttribute("customers", customerRepository.findAll())
                .build();
    }

}


@Component
class CustomerHandler {

    private final CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(customerRepository.findAll(), Customer.class));
    }
}


@Component
class CustomerRepository {

    private final ReactiveRedisOperations<String, Customer> template;

    public CustomerRepository(ReactiveRedisOperations<String, Customer> template) {
        this.template = template;
    }

    public Flux<Customer> findAll() {
        return template.<String, Customer>opsForHash().values("customers");
    }

    public Mono<Customer> save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID().toString());
        }
        return template.<String, Customer>opsForHash()
                .put("customers", customer.getId(), customer)
                .log()
                .map(c -> customer);
    }

    public Mono<Boolean> deleteAll() {
        return template.<String, Customer>opsForHash().delete("customers");
    }
}

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

@Data
class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}

