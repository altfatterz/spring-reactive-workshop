package ch.open.reactiveclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class PerformanceTestReactiveClient {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceTestReactiveClient.class, args);
    }

    private final WebClient webClient = WebClient.builder().build();

    @GetMapping("/")
    public Mono<String> reactiveEndpoint() {
        return webClient.get().uri("http://localhost:9090").retrieve().bodyToMono(String.class).log();
    }
}
