package com.example;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import reactor.test.StepVerifier;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = CustomerServiceTest.Initializer.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @ClassRule
    public static GenericContainer postgres = new GenericContainer<>("postgres:11.4")
            .withExposedPorts(5432).withEnv("POSTGRES_PASSWORD", "s3cr3t");

    @BeforeClass
    public static void setup() throws IOException, InterruptedException {
        postgres.execInContainer("/bin/bash", "-c", "psql -h localhost -U postgres -d postgres -c \"create table customers (id BIGSERIAL, name VARCHAR, PRIMARY KEY(id));\"");
    }

    @Test
    public void saveAllv1() {
        StepVerifier.create(this.customerRepository.deleteAll())
                .verifyComplete();

        StepVerifier.create(this.customerService.saveAllv1("John Doe", "Jane Doe"))
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerService.saveAllv1("John Doe", "", "Jane Doe"))
                .expectNextCount(1).expectError().verify();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();
    }


    @Test
    public void saveAllv2() {
        StepVerifier.create(this.customerRepository.deleteAll())
                .verifyComplete();

        StepVerifier.create(this.customerService.saveAllv2("John Doe", "Jane Doe"))
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerService.saveAllv2("John Doe", "", "Jane Doe"))
                .expectNextCount(1).expectError().verify();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();
    }

    @Test
    public void saveAllv3() {
        StepVerifier.create(this.customerRepository.deleteAll())
                .verifyComplete();

        StepVerifier.create(this.customerService.saveAllv3("John Doe", "Jane Doe"))
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerService.saveAllv3("John Doe", "", "Jane Doe"))
                .expectNextCount(1).expectError().verify();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.r2dbc.url=" + "r2dbc:postgresql://postgres:s3cr3t@localhost:" + postgres.getMappedPort(5432) + "/postgres"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }


}
