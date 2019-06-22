package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;


//    @Test
    public void saveAll() {
        StepVerifier.create(this.customerRepository.deleteAll())
                .verifyComplete();

        // MongoClientException: Sessions are not supported by the MongoDB cluster to which this client is connected
        // The collection needs to exist
        StepVerifier.create(this.customerService.saveAll("John Doe", "Jane Doe"))
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();

        StepVerifier.create(this.customerService.saveAll("Miki", ""))
                .expectNextCount(1).expectError().verify();

        StepVerifier.create(this.customerRepository.findAll())
                .expectNextCount(2).verifyComplete();



    }

    @Test
    public void dummy() {
    }

}
