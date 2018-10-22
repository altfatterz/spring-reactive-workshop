package com.example.productservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ProductServiceApplicationTests {

    @Autowired
    WebTestClient client;

    @Test
    public void getProduct() {
        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("id", 1);
        expected.put("name", "LEGO city");

        this.client.get().uri("/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(MAP_TYPE).isEqualTo(expected);
    }

    @Test
    public void getProducts() {
        this.client.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$[0].name").isEqualTo("LEGO city")
                .jsonPath("$[1].name").isEqualTo("L.O.L. Surprise")
                .jsonPath("$[2].name").isEqualTo("Fire TV Stick with Alexa Voice Remote")
                .jsonPath("$[3].name").isEqualTo("Red Dead Redemption 2")
                .jsonPath("$[4].name").isEqualTo("The Wonky Donkey");
    }

    @Test
    public void getProductEvents() {
        Flux<Map<String, ?>> body = this.client.get().uri("/events")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/event-stream;charset=UTF-8")
                .returnResult(MAP_TYPE)
                .getResponseBody()
                .take(2);

        StepVerifier.create(body)
                .consumeNextWith(map -> assertEquals("LEGO city", map.get("name")))
                .consumeNextWith(map -> assertEquals("L.O.L. Surprise", map.get("name")))
                .verifyComplete();
    }

    @Test
    public void getReviews() {
        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("productId", 1);
        expected.put("review", "good");

        this.client.get().uri("/products/1/reviews")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(MAP_TYPE).isEqualTo(expected);

    }

    private static final ParameterizedTypeReference<Map<String, ?>> MAP_TYPE =
            new ParameterizedTypeReference<Map<String, ?>>() {};

}
