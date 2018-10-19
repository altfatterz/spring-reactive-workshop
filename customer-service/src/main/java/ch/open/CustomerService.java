package ch.open;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class CustomerService {

    private final WebClient webClient;

    public CustomerService(WebClient.Builder builder) {
        this.webClient = builder
                .filter(basicAuthentication("user", "password"))
                .baseUrl("http://example.org").build();
    }

    public Mono<Customer> getCustomer(String name) {
        return webClient.get().uri("/customers/{name}").retrieve().bodyToMono(Customer.class);
    }
}
