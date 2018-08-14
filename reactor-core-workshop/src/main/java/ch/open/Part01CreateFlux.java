package ch.open;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Learn how to create Flux instances.
 *
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 *
 */
public class Part01CreateFlux {

//======================================================================================================================

    // TODO Return an empty Flux
    Flux<String> emptyFlux() {
        return Flux.empty(); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Flux which will never signal any data, error or completion signal
    Flux<String> neverCompletingFlux() {
        return Flux.never(); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Flux which emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

//======================================================================================================================

    // TODO Return a Flux which contains values "foo" and "bar" using Flux#just() method
    Flux<String> fluxFromJust() {
        return Flux.just("foo", "bar"); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Flux from an array which contains 2 values "foo" and "bar"
    Flux<String> fluxFromArray() {
        return Flux.fromArray(new String[] {"foo", "bar"}); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Flux from a list which contains 2 values "foo" and "bar"
    Flux<String> fluxFromIterable() {
        return Flux.fromIterable(Arrays.asList("foo", "bar")); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a Flux from a Stream which contains 2 values "foo" and "bar"
    Flux<String> fluxFromJavaStream() {
        return Flux.fromStream(Stream.of("foo", "bar")); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Return a flux that will emit integers from 5 till 10 inclusive
    Flux<Integer> fromRange() {
        return Flux.range(5, 6); // TO BE REMOVED
    }

//======================================================================================================================

    // TODO Create a Flux that emits increasing values from 0 to 5 each 100ms
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100)).take(5); // TO BE REMOVED
    }

//======================================================================================================================

}
