package ch.open;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part05HandlingErrorsTest {

    Part05HandlingErrors workshop = new Part05HandlingErrors();

    @Test
    public void fallbackMonoOnError() {
        Mono<String> mono = workshop.fallbackMonoOnError(Mono.error(new IllegalStateException()));
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();

        mono = workshop.fallbackMonoOnError(Mono.error(new IllegalArgumentException()));
        StepVerifier.create(mono)
                .expectError().verify();

        mono = workshop.fallbackMonoOnError(Mono.just("bar"));
        StepVerifier.create(mono)
                .expectNext("bar")
                .verifyComplete();
    }

    @Test
    public void fallbackFluxOnError() {
        Flux<String> flux = workshop.fallbackFluxOnError(Flux.error(new IllegalStateException("boom")));
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();

        flux = workshop.fallbackFluxOnError(Flux.error(new IllegalStateException()));
        StepVerifier.create(flux)
                .expectError().verify();

        flux = workshop.fallbackFluxOnError(Flux.just("hello"));
        StepVerifier.create(flux)
                .expectNext("hello")
                .verifyComplete();

    }

}
