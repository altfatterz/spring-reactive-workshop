package ch.open.iot.cloud.simulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
public class SimulatorRestController {

    private Mono<Void> simulator;

    private Disposable task;

    public SimulatorRestController(MeasurementGenerator generator, WebClient webClient) {
        Flux<Measurement> measurements = generator.fetchMeasurementStream(Duration.ofSeconds(2));

        simulator = webClient.post()
                .uri("/measurements")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(measurements, Measurement.class)
                // .exchange() provides a ClientResponse with status, headers and body.
                // It is alternative to .retrieve() to fetch the body directly.
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(Void.class));

    }

    @PostMapping("/start")
    public String start() {
        if (task == null || task.isDisposed()) {
            task = simulator.subscribe();
            return "Started";
        }
        return "Ignored";
    }

    @PostMapping("/stop")
    public String stop() {
        if (task != null) {
            task.dispose();
            return "Cancelled";
        }
        return "Ignored";
    }


}
