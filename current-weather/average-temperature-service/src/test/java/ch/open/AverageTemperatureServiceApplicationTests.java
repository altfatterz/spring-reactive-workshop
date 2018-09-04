package ch.open;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AverageTemperatureServiceApplicationTests {

    // needs a running redis instance

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @Before
    public void setup() {
        this.webTestClient = WebTestClient
                .bindToApplicationContext(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void noCredentials() throws Exception {
        this.webTestClient
                .get()
                .uri("/measurements")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    public void getMeasurementsOk() throws Exception {
        this.webTestClient
                .get()
                .uri("/measurements")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$").isArray();
    }

    @Test
    @WithMockUser
    public void addMeasurementForbidden() throws Exception {
        webTestClient
                .post()
                .uri("/measurements")
                .body(BodyInserters.fromObject(
                        Measurement.builder().sensorName("sensor1")
                                .temperature(BigDecimal.valueOf(12.13))
                                .time(LocalDateTime.now())
                                .build()))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addMeasurementOk() throws Exception {
        webTestClient
                .post()
                .uri("/measurements")
                .body(BodyInserters.fromObject(
                        Measurement.builder().sensorName("foo")
                                .temperature(BigDecimal.valueOf(10.50))
                                .time(LocalDateTime.now())
                                .build()))
                .exchange()
                .expectHeader().exists("Location")
                .expectStatus().isCreated();
    }

}
