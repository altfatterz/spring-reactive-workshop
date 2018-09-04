package ch.open;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Slf4j
class DataInitializer implements CommandLineRunner {

    private final MeasurementRepository measurementRepository;

    public DataInitializer(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public void run(String[] args) {
        log.info("init ...");
        this.measurementRepository
                .deleteAll()
                .thenMany(Flux
                        .just(Measurement.builder()
                                        .sensorName("sensor1")
                                        .temperature(BigDecimal.valueOf(15.16))
                                        .time(LocalDateTime.now())
                                        .build(),
                                Measurement.builder()
                                        .sensorName("sensor2")
                                        .temperature(BigDecimal.valueOf(51.12))
                                        .time(LocalDateTime.now())
                                        .build())
                        .flatMap(measurement -> this.measurementRepository.save(measurement)))
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("init done")
                );
    }

}