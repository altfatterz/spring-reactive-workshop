package ch.open.iot.cloud.simulator;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MeasurementGenerator {

    private final MathContext mathContext = new MathContext(2);

    private final Random random = new Random();

    private final List<Measurement> measurements = new ArrayList<>();

    public MeasurementGenerator() {
        measurements.add(new Measurement("Oeschinensee", BigDecimal.valueOf(15.5)));
        measurements.add(new Measurement("Seebergsee", BigDecimal.valueOf(10.8)));
        measurements.add(new Measurement("Thunersee", BigDecimal.valueOf(12.1)));
    }

    public Flux<Measurement> fetchMeasurementStream(Duration duration) {
        return Flux.interval(duration)
                .map(i -> {
                    Measurement measurement = measurements.get(random.nextInt(3));
                    BigDecimal measurementChange = measurement.getTemperature().multiply(
                            new BigDecimal(0.05 * this.random.nextDouble()), this.mathContext);
                    return new Measurement(measurement.getSensorName(),
                            measurement.getTemperature().add(measurementChange), LocalDateTime.now());
                }).log("generator");

    }


}
