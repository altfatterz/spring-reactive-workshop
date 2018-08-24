package ch.open.temperaturedashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Measurement {

    private String sensorName;

    private BigDecimal temperature;

    private LocalDateTime time;

}
