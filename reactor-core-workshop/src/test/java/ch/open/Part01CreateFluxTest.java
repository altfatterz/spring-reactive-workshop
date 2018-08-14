package ch.open;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Learn how to create Flux instances.
 *
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01CreateFluxTest {

    Part01CreateFlux workshop = new Part01CreateFlux();

//======================================================================================================================

    @Test
    public void emptyFlux() {
        Flux<String> flux = workshop.emptyFlux();

        StepVerifier.create(flux).verifyComplete();
    }

//======================================================================================================================

    @Test
    public void neverCompletingFlux() {
        Flux<String> flux = workshop.neverCompletingFlux();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();

    }

//======================================================================================================================

    @Test
    public void errorFlux() {
        Flux<String> flux = workshop.errorFlux();

        StepVerifier.create(flux)
                .verifyError(IllegalStateException.class);
    }

//======================================================================================================================


    @Test
    public void fluxFromJust() {
        Flux<String> flux = workshop.fluxFromJust();

        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void fluxFromArray() {
        Flux<String> flux = workshop.fluxFromArray();

        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void fluxFromIterable() {
        Flux<String> flux = workshop.fluxFromIterable();

        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void fluxFromJavaStream() {
        Flux<String> flux = workshop.fluxFromJavaStream();

        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void fromRange() {
        Flux<Integer> flux = workshop.fromRange();

        StepVerifier.create(flux)
                .expectNext(5, 6, 7, 8, 9, 10)
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void counter() {
        Flux<Long> flux = workshop.counter();

        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L, 3L, 4L)
                .verifyComplete();
    }

//======================================================================================================================

}
