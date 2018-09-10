package ch.open.temperaturedashboard;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class Measurement {

    private String sensorName;

    private BigDecimal temperature;

    private LocalDateTime time;

}
