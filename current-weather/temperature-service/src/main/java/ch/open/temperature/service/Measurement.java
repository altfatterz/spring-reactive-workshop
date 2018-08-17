package ch.open.temperature.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Integer temperature;

    @NonNull
    private LocalDateTime time;

}