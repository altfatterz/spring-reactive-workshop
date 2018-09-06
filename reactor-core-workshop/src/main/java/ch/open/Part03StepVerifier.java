package ch.open;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */

public class Part03StepVerifier {

    public void expectSubscriptionAndThenComplete(Flux<String> flux) {
        // fail // TO BE ADDED

        StepVerifier.create(flux)
                .expectSubscription()
                .expectComplete()
                .verify(); // TO BE REMOVED
    }

    public void expectItemsAndThenComplete(Flux<String> flux) {
        // fail() // TO BE ADDED
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete(); // TO BE REMOVED
    }

    public void expectItemsAndThenError(Flux<String> flux) {
        // fail() // TO BE ADDED
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .expectError()
                .verify(); // TO BE REMOVED
    }

    public void expectItemsWithAndThenComplete(Flux<Customer> customers) {
        // fail() // TO BE ADDED
        StepVerifier.create(customers)
                .assertNext(customer -> assertThat(customer.getName()).isEqualTo("John"))
                .assertNext(customer -> assertThat(customer.getName()).isEqualTo("Jane"))
                .verifyComplete(); // TO BE REMOVED
    }

    public void expect5Items(Flux<Long> numbers) {
        // fail() // TO BE ADDED
        StepVerifier.create(numbers)
                .expectNextCount(5)
                .verifyComplete(); // TO BE REMOVED
    }

    public void expect3600Items(Supplier<Flux<Long>> supplier) {
        // fail() // TO BE ADDED

        StepVerifier.withVirtualTime(supplier)
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .verifyComplete(); // TO BE REMOVED

    }




    @Data
    @AllArgsConstructor
    static class Customer {

        String name;
    }

    private void fail() {
        throw new AssertionError("not implemented");
    }
}

