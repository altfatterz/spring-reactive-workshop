package ch.open.temperature.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor
public class Measurement {

    private String id;

    @NonNull
    private String sensorName;

    @NonNull
    private BigDecimal temperature;

    @NonNull
    private Instant time;

}