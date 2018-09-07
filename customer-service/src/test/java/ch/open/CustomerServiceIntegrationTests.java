package ch.open;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import redis.embedded.RedisServer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerServiceIntegrationTests {

    static RedisServer redisServer = new RedisServer(6389);

    @Autowired
    private WebTestClient webTestClient;

    @BeforeClass
    public static void setup() {
        redisServer.start();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void getCustomers() {
        webTestClient
                .get().uri("/customers")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser
    public void customersWhenUserIsForbidden() {
        webTestClient
                .get().uri("/customers")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void customersWhenUserIsNotAuthorized() {
        webTestClient
                .get().uri("/customers")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @AfterClass
    public static void shutdown() {
        redisServer.stop();
    }

}
