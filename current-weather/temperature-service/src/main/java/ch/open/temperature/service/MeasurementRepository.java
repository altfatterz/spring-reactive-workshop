package ch.open.temperature.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MeasurementRepository extends ReactiveMongoRepository<Measurement, String> {

    @Tailable
    Flux<Measurement> findAllBy();
}
