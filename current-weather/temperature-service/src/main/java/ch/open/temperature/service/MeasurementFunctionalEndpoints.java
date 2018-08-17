package ch.open.temperature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MeasurementFunctionalEndpoints {

    @Autowired
    private MeasurementHandler handler;

    @Bean
    RouterFunction<?> router() {
        return route(GET("/measurements").and(accept(MediaType.APPLICATION_JSON)), handler::getMeasurements)
                .andRoute(GET("/measurements").and(accept(APPLICATION_STREAM_JSON)), handler::getMeasurementsAsStream);
    }

}
