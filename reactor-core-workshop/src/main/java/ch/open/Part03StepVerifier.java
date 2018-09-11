package ch.open;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Learn how to use StepVerifier to test Mono and Flux.
 *
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */

public class Part03StepVerifier {

    /**
     * TODO 1
     * <p>
     * Use the {@link StepVerifier} to check that the flux parameter emits subscription event and completes successfully
     */
    public void expectSubscriptionAndThenComplete(Flux<String> flux) {
        // fail // TO BE ADDED

        StepVerifier.create(flux)
                .expectSubscription()
                .expectComplete()
                .verify(); // TO BE REMOVED
    }

    /**
     * TODO 2
     * <p>
     * Use the {@link StepVerifier} to check that the flux parameter emits "foo" and "bar" items and completes successfully
     */
    public void expectItemsAndThenComplete(Flux<String> flux) {
        // fail() // TO BE ADDED
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete(); // TO BE REMOVED
    }

    /**
     * TODO 3
     * <p>
     * Use the {@link StepVerifier} to check that the flux parameter emits "foo" and "bar" items and then an error with "error" message
     */
    public void expectItemsAndThenError(Flux<String> flux) {
        // fail() // TO BE ADDED
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .expectErrorMessage("error")
                .verify(); // TO BE REMOVED
    }

    /**
     * TODO 4
     * <p>
     * Use the {@link StepVerifier} to check that the customers parameter emits a Customer with name "John" and then
     * a Customer with name "Jane" and then completes successfully
     */
    public void expectItemsWithAndThenComplete(Flux<Customer> customers) {
        // fail() // TO BE ADDED
        StepVerifier.create(customers)
                .assertNext(customer -> assertThat(customer.getName()).isEqualTo("John"))
                .assertNext(customer -> assertThat(customer.getName()).isEqualTo("Jane"))
                .verifyComplete(); // TO BE REMOVED
    }

    /**
     * TODO 5
     * <p>
     * Use the {@link StepVerifier} to check that the numbers parameter emits 10 items and completes successfully.
     * Notice how long the test takes.
     */
    public void expect5Items(Flux<Long> numbers) {
        // fail() // TO BE ADDED
        StepVerifier.create(numbers)
                .expectNextCount(5)
                .verifyComplete(); // TO BE REMOVED
    }

    /**
     * TODO 6
     * <p>
     * Use the {@link StepVerifier} to check that 3600 items are emitted at intervals of 1 second.
     * Verify it quicker than 3600 seconds.
     *
     * Note that here we need to provide a Supplier, since if we provide a Flux then it would be already created with a real
     * Scheduler and StepVerifier can not set up a virtual time Scheduler.
     *
     * Look into {@link StepVerifier#withVirtualTime(Supplier)}
     */
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

