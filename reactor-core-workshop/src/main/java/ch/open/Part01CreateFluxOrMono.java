package ch.open;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Part01CreateFluxOrMono {

    // TODO create a sequence that emits a single value from the provided Optional<String> parameter
    Publisher<String> createFromOptional(Optional<String> foo) {
        return Mono.justOrEmpty(foo); // TO BE REMOVED
    }

    // TODO create a sequence that emits a single value from the provided potentially null value
    Publisher<String> createFromPotentiallyNull(String foo) {
        return Mono.justOrEmpty(foo); // TO BE REMOVED
    }

    // TODO create a sequence that emits a single value from the provided lazily captured value
    Publisher<String> createFromSupplier(Supplier<String> foo) {
        return Mono.fromSupplier(foo); // TO BE REMOVED
    }

    // TODO create a sequence that completes empty once the provided Runnable completed
    Publisher<String> createFromRunnable(Runnable runnable) {
        return Mono.fromRunnable(runnable); // TO BE REMOVED
    }

    // TODO create a sequence producing its value using the provided Callable
    Publisher<String> createFromCallable(Callable<String> callable) {
        return Mono.fromCallable(callable); // TO BE REMOVED
    }

    // TODO create a sequence producing its value using the provided CompletableFuture
    Publisher<String> createFromFuture(CompletableFuture<String> future) {
        return Mono.fromFuture(future); // TO BE REMOVED
    }

    // TODO create a sequence that emits "foo" and "bar" strings
    Publisher<String> createFooBar() {
        return Flux.just("foo", "bar"); // TO BE REMOVED
    }

    // TODO create a sequence from the provided array
    Publisher<String> createFromArray(String[] array) {
        return Flux.fromArray(array); // TO BE REMOVED
    }

    // TODO create a sequence from the provided list
    Publisher<String> createFromArray(List<String> values) {
        return Flux.fromIterable(values); // TO BE REMOVED
    }

    // TODO create a sequence that emits the items contained in the provided stream
    Publisher<String> createFromStream(Stream<String> stream) {
        return Flux.fromStream(stream); // TO BE REMOVED
    }

    // TODO create a sequence that emits the items contained in the provided stream for each subscription
    Publisher<String> createFromStreamForEachSubscriber(Stream<String> stream) {
        return Flux.fromStream(() -> stream); // TO BE REMOVED
    }

    // TODO create a sequence that completes without emitting any item.
    Publisher<Void> createEmpty() {
        return Mono.empty(); // or Flux.empty() // TO BE REMOVED
    }

    // TODO create a sequence that terminates with IllegalStateException error immediately after being subscribed to.
    Publisher<Void> createError() {
        return Mono.error(new IllegalStateException()); // or Flux.error(new IllegalStateException()) // TO BE REMOVED
    }

    // TODO create a sequence that will never signal any data, error or completion signal
    Publisher<Void> neverEmit() {
        return Mono.never(); // or Flux.never(); // TO BE REMOVED
    }

    // TODO lazily supply the provided publisher every time a subscription is made on the resulting Flux
    Flux<String> deferFlux(Publisher<String> publisher) {
        return Flux.defer(() -> publisher); // TO BE REMOVED
    }

    // TODO create a sequence that emits increasing values from 0 to 5 each 100ms
    Flux<Long> counter() {
        Flux.range(10, 13);

        return Flux.interval(Duration.ofMillis(100)).take(5); // TO BE REMOVED
    }

}
