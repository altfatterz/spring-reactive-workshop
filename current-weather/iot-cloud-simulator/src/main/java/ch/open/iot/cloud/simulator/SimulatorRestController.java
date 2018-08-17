package ch.open.iot.cloud.simulator;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SimulatorRestController {

    private Mono<Void> simulator;

    private Disposable task;

    public SimulatorRestController(MeasurementGenerator generator, WebClient webClient) {
        Flux<Measurement> measurements = generator.generate();

        simulator = webClient.post()
                .uri("/measurements")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(measurements, Measurement.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @PostMapping("/start")
    public void start() {
        task = simulator.subscribe();
    }

    @PostMapping("/stop")
    public void stop() {
        task.dispose();
    }


}
