package ch.open.iot.cloud.simulator;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class MeasurementSimulator implements ApplicationRunner {

    private WebClient client = WebClient.create("http://localhost:8081");

    private MeasurementGenerator generator;

    public MeasurementSimulator(MeasurementGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(ApplicationArguments args) {
        Flux<Measurement> measurements = generator.generate();

        client.post()
                .uri("/measurements")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(measurements, Measurement.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
