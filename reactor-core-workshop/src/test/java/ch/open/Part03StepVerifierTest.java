package ch.open;

import ch.open.Part03StepVerifier.Customer;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Learn how to use StepVerifier to test Mono and Flux publishers.
 *
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifierTest {

    Part03StepVerifier workshop = new Part03StepVerifier();

    @Test
    public void expectSubscriptionAndThenComplete() {
        workshop.expectSubscriptionAndThenComplete(Flux.empty());
    }

    @Test
    public void expectItemsAndThenComplete() {
        workshop.expectItemsAndThenComplete(Flux.just("foo", "bar"));
    }

    @Test
    public void expectItemsAndThenError() {
        workshop.expectItemsAndThenError(Flux.just("foo", "bar").concatWith(Mono.error(new RuntimeException())));
    }

    @Test
    public void expectItemsWithAndThenComplete() {
        workshop.expectItemsWithAndThenComplete(Flux.just(
                new Customer("John"), new Customer("Jane")));
    }

    @Test
    public void expectNextCount() {
        workshop.expect5Items(Flux.interval(Duration.ofSeconds(1)).take(5));
    }

    @Test
    public void virtualTime() {
        workshop.expect3600Items(() -> Flux.interval(Duration.ofSeconds(1)).take(3600));
    }

}
