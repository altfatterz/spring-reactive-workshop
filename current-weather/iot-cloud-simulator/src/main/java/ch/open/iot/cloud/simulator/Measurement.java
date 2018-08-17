package ch.open.iot.cloud.simulator;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class Measurement {

    private String id;

    @NonNull
    private String sensorName;

    @NonNull
    private Integer temperature;

    @NonNull
    private LocalDateTime time;

}
