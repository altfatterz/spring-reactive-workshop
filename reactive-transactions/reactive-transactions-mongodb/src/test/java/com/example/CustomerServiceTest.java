package com.example;

import org.junit.Ignore;
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

    @Test
    @Ignore // needs a running MongoDB cluster, see the README.md how to set it up
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
    @Ignore // needs a running MongoDB cluster, see the README.md how to set it up
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
    @Ignore // needs a running MongoDB cluster, see the README.md how to set it up
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
    @Ignore // needs a running MongoDB cluster, see the README.md how to set it up
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


}
