package ch.open.loadgenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootApplication
public class LoadGenerator implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LoadGenerator.class, args);
    }

    @Override
    public void run(String... args) {
        int nr_concurrent_clients = Integer.valueOf(args[0]);
        String port = args[1];

        WebClient webClient = WebClient.create("http://localhost:" + port);

        Mono<String> response = webClient.get().exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("concurrent access: " + nr_concurrent_clients);

        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < nr_concurrent_clients; i++) {
            futures.add(response.toFuture());
        }

        for (CompletableFuture<?> future : futures) {
            future.join();
        }

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        System.exit(0);
    }
}
