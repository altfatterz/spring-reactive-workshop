package ch.open;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Learn how to create Mono instances.
 *
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02CreateMono {

//======================================================================================================================

    // TODO Return an empty Mono
    Mono<String> emptyMono() {
        return Mono.empty(); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Mono that never emits any signal
    Mono<String> monoWithNoSignal() {
        return Mono.never();  // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Mono that contains a "foo" value
    Mono<String> monoWithValue() {
        return Mono.just("foo"); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Create a Mono that emits an IllegalStateException
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException()); // TO BE REMOVED
    }

//======================================================================================================================

    /**
     * We can also create a stream from Future, making easier to switch from legacy code to reactive
     * Since CompletableFuture can only return a single entity, Mono is the returned type when converting from a Future
     */
    // TODO Create a Mono from a Future returning a "foo" value
    Mono<String> monoFromFuture() {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> "foo")); // TO BE REMOVED
    }

//======================================================================================================================


}
