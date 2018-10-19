package ch.open;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class WebConfig {
}
