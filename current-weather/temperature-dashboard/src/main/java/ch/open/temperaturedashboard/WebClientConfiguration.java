package ch.open.temperaturedashboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    WebClient webClient() {
        return WebClient
                .create("http://localhost:8081")
                .mutate()
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .build();
    }
}
