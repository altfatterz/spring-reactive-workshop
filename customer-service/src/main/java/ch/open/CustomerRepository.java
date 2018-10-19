package ch.open;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class CustomerRepository {

    private final ReactiveRedisOperations<String, Customer> template;

    public CustomerRepository(ReactiveRedisOperations<String, Customer> template) {
        this.template = template;
    }

    public Flux<Customer> findAll() {
        return template.<String, Customer>opsForHash().values("customers");
    }

    public Mono<Customer> save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID().toString());
        }
        return template.<String, Customer>opsForHash()
                .put("customers", customer.getId(), customer)
                .log()
                .map(c -> customer);
    }


    public Mono<Boolean> deleteAll() {
        return template.<String, Customer>opsForHash().delete("customers");
    }

    public Mono<Customer> findById(Long id) {
        return Mono.empty();
    }
}
