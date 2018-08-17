package ch.open.temperature.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Log
public class MeasurementHandler {

    private final MeasurementRepository repository;

    public MeasurementHandler(MeasurementRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getMeasurementsAsStream(ServerRequest request) {
        log.info("get measurements as stream");
        return ok().contentType(APPLICATION_STREAM_JSON).body(repository.findAllBy().log(), Measurement.class);
    }

    public Mono<ServerResponse> getMeasurements(ServerRequest request) {
        log.info("get measurements");
        return ok().body(repository.findAll().log(), Measurement.class);
    }

}
