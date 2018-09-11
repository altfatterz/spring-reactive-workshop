package ch.open;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Learn about creating Mono or Flux publishers.
 * <p>
 * Mono: https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/mono.png
 * <p>
 * Flux: https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/flux.png
 */
public class Part01CreateFluxOrMono {

    /**
     * TODO 1
     * <p>
     * Create a sequence that emits a single value from the provided Optional<String> parameter.
     */
    Publisher<String> createFromOptional(Optional<String> foo) {
        return null;
    }

    /**
     * TODO 2
     * <p>
     * Create a sequence that emits a single value from the provided potentially null value.
     */
    Publisher<String> createFromPotentiallyNull(String foo) {
        return null;
    }

    /**
     * TODO 3
     * <p>
     * Create a sequence that emits a single value from the provided lazily captured value.
     */
    Publisher<String> createFromSupplier(Supplier<String> foo) {
        return null;
    }

    /**
     * TODO 4
     * <p>
     * Create a sequence producing its value using the provided CompletableFuture parameter.
     */
    Publisher<String> createFromFuture(CompletableFuture<String> future) {
        return null;
    }

    /**
     * TODO 5
     * <p>
     * Create a sequence producing its value using the provided Callable.
     */
    Publisher<String> createFromCallable(Callable<String> callable) {
        return null;
    }

    /**
     * TODO 6
     * <p>
     * Create a sequence that emits "foo" and "bar" strings.
     */
    Publisher<String> createFooBar() {
        return null;
    }

    /**
     * TODO 7
     * <p>
     * Create a sequence from the provided array.
     */
    Publisher<String> createFromArray(String[] array) {
        return null;
    }

    /**
     * TODO 8
     * <p>
     * Create a sequence from the provided list.
     */
    Publisher<String> createFromList(List<String> values) {
        return null;
    }

    /**
     * TODO 9
     * <p>
     * Create a sequence that emits the items contained in the provided stream.
     */
    Publisher<String> createFromStream(Stream<String> stream) {
        return null;
    }

    /**
     * TODO 10
     * <p>
     * Create a sequence that completes without emitting any item.
     */
    Publisher<Void> createEmpty() {
        return null;
    }

    /**
     * TODO 11
     * <p>
     * Create a sequence that terminates with {@link IllegalStateException} error immediately after being subscribed to.
     */
    Publisher<Void> createError() {
        return null;
    }

    /**
     * TODO 12
     * <p>
     * Create a sequence that will never signal any data, error or completion signal.
     */
    Publisher<Void> neverEmit() {
        return null;
    }

    /**
     * TODO 13
     * <p>
     * Lazily supply the provided publisher every time a subscription is made on the resulting Flux.
     */
    Flux<Integer> lazy(Supplier<Publisher<Integer>> supplier) {
        return null;
    }

    /**
     * TODO 14
     * <p>
     * Create a sequence that emits increasing values from 0 to 5 each 100ms.
     */
    Publisher<Long> counter() {
        return null;
    }

    /**
     * TODO 15
     * <p>
     * Create a sequence that will emit integers from 5 till 10 inclusive.
     */
    Publisher<Integer> fromRange() {
        return null;
    }

}
