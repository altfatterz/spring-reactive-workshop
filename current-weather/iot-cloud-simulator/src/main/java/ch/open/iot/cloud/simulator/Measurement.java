package ch.open.iot.cloud.simulator;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Measurement {

    @NonNull
    private String sensorName;

    @NonNull
    private BigDecimal temperature;

    @NonNull
    private LocalDateTime time;

    public Measurement(String sensorName, BigDecimal temperature, LocalDateTime time) {
        this.sensorName = sensorName;
        this.temperature = temperature;
        this.time = time;
    }

    public Measurement(String sensorName, BigDecimal temperature) {
        this(sensorName, temperature, LocalDateTime.now());
    }
}
