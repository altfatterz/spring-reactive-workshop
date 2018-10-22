package com.example.orderservice;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore // product-service must be running
public class OrderServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void product() {
        this.webTestClient.get().uri("/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Product.class).isEqualTo(new Product(1L, "LEGO city"));
    }

    @Test
    public void products() {
        this.webTestClient.get().uri("/products")
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
    public void events() {
        Flux<Product> body = this.webTestClient.get().uri("/events")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/event-stream;charset=UTF-8")
                .returnResult(Product.class)
                .getResponseBody()
                .take(3);
        StepVerifier.create(body)
                .expectNext(new Product(1L, "LEGO city"))
                .expectNext(new Product(2L, "L.O.L. Surprise"))
                .expectNext(new Product(3L, "Fire TV Stick with Alexa Voice Remote"))
                .verifyComplete();
    }

    @Test
    public void orders() {
        this.webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].product").isEqualTo("Red Dead Redemption 2")
                .jsonPath("$[0].review").isEqualTo("bad")
                .jsonPath("$[1].product").isEqualTo("The Wonky Donkey")
                .jsonPath("$[1].review").isEqualTo("the best")
                .jsonPath("$[2].product").isEqualTo("L.O.L. Surprise")
                .jsonPath("$[2].review").isEqualTo("could be better");
    }
}
