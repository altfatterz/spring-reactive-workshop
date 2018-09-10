package ch.open.reactiveclientwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ReactiveClientWebflux {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveClientWebflux.class, args);
    }

    private final WebClient webClient = WebClient.builder().build();

    @GetMapping("/")
    public Mono<String> reactiveEndpoint() {
        return webClient.get().uri("http://localhost:9090").exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
    }
}
