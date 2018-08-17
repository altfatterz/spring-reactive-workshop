package ch.open.iot.cloud.simulator;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class MeasurementGenerator {

    private Random random = new Random();

    private String[] SENSOR_NAMES = new String[]{"Oeschinensee", "Seebergsee", "Thunersee"};

    public Flux<Measurement> generate() {
        return Flux
                .interval(Duration.ofSeconds(2))
                .map(i -> Measurement.builder()
                        .sensorName(SENSOR_NAMES[random.nextInt(3)])
                        .temperature(random.nextInt(12) + 7)
                        .time(LocalDateTime.now())
                        .build()).log();
    }


}
