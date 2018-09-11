package ch.open;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;

/**
 * @WebFluxTest auto-configures the Spring WebFlux infrastructure and limits scanned beans to
 * @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, and WebFluxConfigurer.
 *
 * Regular @Component beans are not scanned when the @WebFluxTest annotation is used.
 *
 * @WebFluxTest cannot detect routes registered via the functional web framework.
 * For testing RouterFunction beans in the context, consider importing your RouterFunction yourself via @Import
 */

@RunWith(SpringRunner.class)
@WebFluxTest
@Import({RouterFunctionConfig.class, CustomerHandler.class})
public class CustomerServiceTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void getCustomers() {
        given(customerRepository.findAll()).willReturn(Flux.just(new Customer("John", "Doe")));

        webTestClient
                .get().uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].firstName").isEqualTo("John")
                .jsonPath("$[0].lastName").isEqualTo("Doe");

    }


}
