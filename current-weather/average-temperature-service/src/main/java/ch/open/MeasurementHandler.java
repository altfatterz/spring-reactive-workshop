package ch.open;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class MeasurementHandler {

    private final MeasurementRepository measurementRepository;

    public MeasurementHandler(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ServerResponse.ok().body(this.measurementRepository.findAll(), Measurement.class);
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        return req.bodyToMono(Measurement.class)
                .flatMap(measurement -> this.measurementRepository.save(measurement))
                .flatMap(measurement -> ServerResponse.created(URI.create("/measurements/" + measurement.getId())).build());
    }


}
