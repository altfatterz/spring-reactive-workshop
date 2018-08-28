package ch.open;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Part05HandlingErrors {

    // TODO Emit "foo" in case IllegalStateException error occurs in the input Mono, else do not change the input Mono.
    public Mono<String> fallbackMonoOnError(Mono<String> mono) {
        return mono.onErrorReturn(IllegalStateException.class, "foo"); // TO BE REMOVED
    }

    // TODO Emit "foo" and "bar" when an error occurs with message "boom" in the input Flux, else do not change the input Flux.
    public Flux<String> fallbackFluxOnError(Flux<String> flux) {
        return flux.onErrorResume(throwable -> "boom".equals(throwable.getMessage()),
                throwable -> Flux.just("foo", "bar")); // TO BE REMOVED
    }

    // demonstrate that onReturnOnError doesn't unsubscribe from the source, the map operator will be still called.

}
