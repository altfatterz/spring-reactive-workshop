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
        return Mono.justOrEmpty(foo);
    }

    // TODO create a sequence that emits a single value from the provided potentially null value
    Publisher<String> createFromPotentiallyNull(String foo) {
        return Mono.justOrEmpty(foo);
    }

    // TODO create a sequence that emits a single value from the provided lazily captured value
    Publisher<String> createFromSupplier(Supplier<String> foo) {
        return Mono.fromSupplier(foo);
    }

    // TODO create a sequence that emits "foo" and "bar" strings
    Publisher<String> createFooBar() {
        return Flux.just("foo", "bar");
    }

    // TODO create a sequence from the provided array
    Publisher<String> createFromArray(String[] array) {
        return Flux.fromArray(array);
    }

    // TODO create a sequence from the provided list
    Publisher<String> createFromArray(List<String> values) {
        return Flux.fromIterable(values);
    }

    // TODO create a sequence that emits the items contained in the provided stream
    Publisher<String> createFromStream(Stream<String> stream) {
        return Flux.fromStream(stream);
    }

    // TODO create a sequence that emits the items contained in the provided stream for each subscription
    Publisher<String> createFromStreamForEachSubscriber(Stream<String> stream) {
        return Flux.fromStream(() -> stream);
    }

    // TODO create a sequence that completes empty once the provided Runnable completed
    Publisher<String> createFromRunnable(Runnable runnable) {
        return Mono.fromRunnable(runnable);
    }

    // TODO create a sequence producing its value using the provided Callable
    Publisher<String> createFromCallable(Callable<String> callable) {
        return Mono.fromCallable(callable);
    }

    // TODO create a sequence producing its value using the provided Supplier
    Publisher<String> createFromCallable(Supplier<String> supplier) {
        return Mono.fromSupplier(supplier);
    }

    // TODO create a sequence producing its value using the provided CompletableFuture
    Publisher<String> createFromFuture(CompletableFuture<String> future) {
        return Mono.fromFuture(future);
    }

    // TODO create a sequence that completes without emitting any item.
    Publisher<Void> createEmpty() {
        return Mono.empty(); // or Flux.empty()
    }

    // TODO create a sequence that terminates with IllegalStateException error immediately after being subscribed to.
    Publisher<Void> createError() {
        return Mono.error(new IllegalStateException()); // or Flux.error(new IllegalStateException())
    }

    // TODO create a sequence that will never signal any data, error or completion signal
    Publisher<Void> neverEmit() {
        return Mono.never(); // or Flux.never();
    }

    // TODO lazily supply the provided publisher every time a subscription is made on the resulting Flux
    Flux<String> deferFlux(Publisher<String> publisher) {
        return Flux.defer(() -> publisher);
    }

    // TODO create a sequence that emits increasing values from 0 to 5 each 100ms
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100)).take(5);
    }

    // TODO create a Flux generating the sequence 0, 2, 4, 6, 8, 10, 12, 14 ... completing by reaching the provided nr of elements.
    // for example
    // for nr = 0 it should be empty
    // for nr = 1 it should produce 0
    // for nr = 8 it should produce 0, 2, 4, 6, 8, 10, 12, 14
    Flux<Integer> generate(int nr) {
        return Flux.generate(
                () -> 0,
                (state, sink) -> {
                    if (state == nr) {
                        sink.complete();
                    }
                    sink.next(2 * state);
                    return state + 1;
                });
    }

    // TODO create a flux emitting 1 and 2 and then complete. Use the Flux#create method
    // Using Flux.create we can handle the actual emissions of events with the events like onNext, onComplete, onError
    // When subscribing to the Flux with flux.subscribe() the lambda code inside create() gets executed.
    // When using Flux.create you need to be aware of back-pressure handling
    public Flux<Integer> createSimpleFlux() {
        return Flux.create(subscriber -> {
            subscriber.next(1);
            subscriber.next(2);
            subscriber.complete();
        });
    }



}
