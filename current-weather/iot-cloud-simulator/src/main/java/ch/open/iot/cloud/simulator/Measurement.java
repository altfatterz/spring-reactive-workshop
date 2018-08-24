package ch.open.iot.cloud.simulator;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Measurement {

    @NonNull
    private String sensorName;

    @NonNull
    private BigDecimal temperature;

    @NonNull
    private Instant time;

    public Measurement(String sensorName, BigDecimal temperature) {
        this.sensorName = sensorName;
        this.temperature = temperature;
        this.time = Instant.now();
    }
}
