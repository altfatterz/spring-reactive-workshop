package ch.open;

import ch.open.customer.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerServiceIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getCustomers() {
        webTestClient
                .get().uri("/customers")
                .exchange()
                .expectStatus().isOk().expectBodyList(Customer.class).hasSize(7);
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

}
