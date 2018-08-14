package ch.open;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Learn how to create Mono instances.
 *
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02CreateMonoTest {

    Part02CreateMono workshop = new Part02CreateMono();

//======================================================================================================================

    @Test
    public void emptyMono() {
        Mono<String> mono = workshop.emptyMono();

        StepVerifier.create(mono)
                .verifyComplete();
    }

//======================================================================================================================

    @Test
    public void monoWithNoSignal() {
        Mono<String> mono = workshop.monoWithNoSignal();

        StepVerifier
                .create(mono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

//======================================================================================================================

    @Test
    public void monoWithValue() {
        Mono<String> mono = workshop.monoWithValue();

        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }


//======================================================================================================================

    @Test
    public void errorMono() {
        Mono<String> mono = workshop.errorMono();

        StepVerifier.create(mono)
                .verifyError(IllegalStateException.class);
    }

//======================================================================================================================

    @Test
    public void monoFromFuture() {
        Mono<String> mono = workshop.monoFromFuture();

        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();

    }
}
