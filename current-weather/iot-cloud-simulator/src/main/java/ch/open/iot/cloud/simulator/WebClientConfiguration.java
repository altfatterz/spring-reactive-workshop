package ch.open.iot.cloud.simulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Bean
    WebClient webClient() {
        return WebClient
                .create("http://localhost:8081")
                .mutate()
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .filter(logRequest())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info(name + "=" + value)));
            return next.exchange(clientRequest);
        };
    }
}
