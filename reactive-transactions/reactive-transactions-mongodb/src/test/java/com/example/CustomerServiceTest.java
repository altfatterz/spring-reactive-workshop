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
    public static GenericContainer mongo = new GenericContainer<>("mongo:4.0.10")
            .withExposedPorts(27017).withCommand("--replSet tx-replica-set");

    @BeforeClass
    public static void setup() throws IOException, InterruptedException {
        mongo.execInContainer("/bin/bash", "-c", "mongo test --eval 'rs.initiate()'");
        mongo.execInContainer("/bin/bash", "-c", "until mongo --eval \"printjson(rs.isMaster())\" | grep ismaster | grep true > /dev/null 2>&1;do sleep 1;done");
        mongo.execInContainer("/bin/bash", "-c", "mongo test --eval 'db.createCollection(\"customers\")'");
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

    @Test
    public void saveAllv4() {
        StepVerifier.create(this.customerRepository.deleteAll())
                .verifyComplete();

        StepVerifier.create(this.customerService.saveAllv4("John Doe", "Jane Doe"))
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerService.saveAllv4("John Doe", "", "Jane Doe"))
                .expectNextCount(1).expectError().verify();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(5).verifyComplete();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.data.mongodb.port=" + mongo.getMappedPort(27017)
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }


}
