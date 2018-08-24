package ch.open.temperature.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor
public class Measurement {

    @JsonIgnore
    private String id;

    @NonNull
    private String sensorName;

    @NonNull
    private BigDecimal temperature;

    @NonNull
    private LocalDateTime time;

}