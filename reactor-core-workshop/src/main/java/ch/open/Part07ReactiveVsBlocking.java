package ch.open;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class Part07ReactiveVsBlocking {

    /**
     * TODO 1
     * <p>
     * Return the Customer emitted by the provided Mono
     */
    public Customer monoToCustomer(Mono<Customer> customer) {
        return null;
    }

    /**
     * TODO 2
     * <p>
     * Return the Customers emitted by the provided Flux
     */
    public Iterable<Customer> fluxToCustomers(Flux<Customer> customers) {
        return null;
    }

    /**
     * TODO 3
     * <p>
     * Create a Flux reading all customers from the blocking {@link BlockingCustomerRepository}
     * deferred until the flux is subscribed.
     * <p>
     * Configure that each subscription will happen on a worker from Schedulers.elastic
     */
    public Flux<Customer> blockingRepositoryToFlux(BlockingCustomerRepository repository) {
        return null;
    }

    /**
     * TODO 4
     * <p>
     * Save the users from the customers Flux into the blocking repository using an elastic scheduler and return a
     * Mono<Void>
     */
    public Mono<Void> fluxToBlockingRepository(Flux<Customer> customers, BlockingCustomerRepository repository) {
        return null;

    }

    static class BlockingCustomerRepository {

        private ReactiveCustomerRepository repository = new ReactiveCustomerRepository();
        private long count;

        public Iterable<Customer> findAll() {
            count++;
            return repository.findAll().toIterable();
        }

        public void save(Customer customer) {
            count++;
            repository.save(Mono.just(customer)).block();
        }

        public long getCount() {
            return count;
        }
    }

    static class ReactiveCustomerRepository {

        private List<Customer> customers = new ArrayList<>();

        public ReactiveCustomerRepository() {
            customers.add(new Customer("John"));
            customers.add(new Customer("Jane"));
        }

        public Flux<Customer> findAll() {
            return Flux.fromIterable(customers);
        }

        public Mono<Void> save(Publisher<Customer> flux) {
            return Flux.from(flux).doOnNext(customer -> customers.add(customer)).then();
        }

    }

    @Data
    @RequiredArgsConstructor
    @EqualsAndHashCode
    static class Customer {
        final String name;
    }
}
