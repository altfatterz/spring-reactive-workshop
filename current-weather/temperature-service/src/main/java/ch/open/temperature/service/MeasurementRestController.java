package ch.open.temperature.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MeasurementRestController {

    private final MeasurementRepository repository;

    public MeasurementRestController(MeasurementRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path = "/measurements", consumes = "application/stream+json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> loadMeasurements(@RequestBody Flux<Measurement> measurements) {
        return this.repository.insert(measurements).then();
    }

}
