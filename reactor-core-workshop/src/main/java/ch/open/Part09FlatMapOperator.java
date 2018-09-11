package ch.open;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Learn about the flatMap operator.
 * <p>
 * https://raw.githubusercontent.com/reactor/projectreactor.io/master/src/main/static/assets/img/marble/flatmap.png
 *
 * <p>
 * map:       takes a Function<T, U> and returns a Flux<U>
 * flatMap:   takes a Function<T, Publisher<V>> and returns a Flux<V>
 * <p>
 * You can pass a Function<T, Publisher<V>> to a map, but it wouldn't know what to do with the Publishers
 * and that would result in a Flux<Publisher<V>>, a sequence of inner publishers.
 * <p>
 * flatMap expects a Publisher<V> for each T.
 * It knows what to do with it: subscribe to it and propagate its elements in the output sequence.
 * <p>
 * As a result, the return type is Flux<V>: flatMap will flatten each inner Publisher<V> into the output sequence of
 * all the Vs.
 */
@Slf4j
public class Part09FlatMapOperator {

    /**
     * TODO 1
     *
     * <p>
     * For each item ("hallo", "hoi", "ciao", "szia") emitted by the greetings Flux execute the
     * {@link #asyncRemoteCall(String)}) and merge the results allowing the items to interleave.
     * <p>
     * Use the {@link Flux#flatMap(Function)} and the {@link Flux#log} operators to check the order of the items and
     * the used threads.
     */
    Flux<String> simpleFlatMap(Flux<String> greetings) {
        return null;
    }

    /**
     * TODO 2
     *
     * <p>
     * For each item ("Joe", "Chandler", "Monica", "Phoebe", "Rachel", "Ross") emitted by the friends Flux execute the
     * {@link #asyncRemoteCall(String)}) NOT allowing the items to interleave.
     * <p>
     * Make sure the return values are all in uppercase.
     * <p>
     * Use {@link Flux#flatMapSequential(Function)} the {@link Flux#log} operators to check the order of the items
     * and the used threads.
     */
    Flux<String> flatMapSequential(Flux<String> friends) {
        return null;
    }

    /**
     * TODO 3
     *
     * <p>
     * For each item ("Bern", "Berlin", "Budapest") emitted by the 'capitals' Flux execute the
     * {@link #asyncRemoteCall(String)} NOT allowing the items to interleave.
     * <p>
     * This time use the '{@link Flux#flatMap(Function, int)}' and the {@link Flux#log} operators to check the
     * order of the items and the used threads.
     */
    // TODO
    Flux<String> flatMapWithConcurrency(Flux<String> capitals) {
        return null;
    }

    /**
     * TODO 4
     *
     * <p>
     * For each item ("Nile", "Danube", "Ganges") emitted by the 'rivers' Flux execute the
     * {@link #asyncRemoteCall(String)} NOT allowing the items to interleave.
     * <p>
     * This time use the {@link Flux#concatMap(Function)} and the {@link Flux#log} operators to check the order
     * of the items and the used threads.
     */
    Flux<String> concatMapExample(Flux<String> rivers) {
        return null;
    }

    /**
     * TODO 5
     *
     * <p>
     * The provided 'colors' Flux is returning color names.
     * Provide the following result, where the 'count' indicates how many times the given color was received
     * <p>
     * ColorCount(color=red, count=2)
     * ColorCount(color=green, count=3)
     * ...
     * <p>
     * Look into {@link Flux#groupBy(Function)}, {@link Flux#count()} and {@link Flux#flatMap(Function)} operators
     */
    Flux<ColorCount> fluxOfFluxes(Flux<String> colors) {
        return null;
    }

    /**
     * TODO 6
     *
     * <p>
     * After every second item emitted by the 'values' Flux emit a "---" value in the returned Flux.
     * <p>
     * For example:
     * "1", "2", "3", "4", "5", "6", "7", "8", "9"
     * -->
     * "1", "2", "---", "3", "4", "---", "5", "6", "---", "7", "8", "---", "9"
     * <p>
     * Look into {@link Flux#window(int)}' and {@link Flux#flatMap(Function, Function, Supplier)} operators.
     */
    Flux<String> complexFlatMap(Flux<String> values) {
        return null;
    }

    /**
     * TODO 7
     *
     * <p>
     * For each item emitted by the 'names' Flux call the {@link #asyncRemoteCallWithErrorSignal(String)} and merge
     * the results allowing the items to interleave
     * <p>
     * Handle the potential error signal by emitting and empty Flux.
     * <p>
     * Look into {@link Flux#flatMap(Function)' and {@link Flux#flatMap(Function, Function, Supplier)} operators
     */
    Flux<String> flatMapOverrideError(Flux<String> names) {
        return null;
    }

    /**
     * TODO 8
     *
     * <p>
     * For the given 'greeting' Mono call the {@link #asyncRemoteCall(String)} and return the result
     * <p>
     * Look into {@link Mono#flatMapMany(Function)} operator
     */
    Flux<String> flatMapManyExample(Mono<String> greeting) {
        return null;
    }


    /**
     * TODO 9
     *
     * <p>
     * For the given 'mountains' Flux call the {@link #syncRemoteCall(String)} and merge the results.
     * Check if the items are interleaved or not.
     * <p>
     * Look into {@link Mono#flatMapIterable(Function)}} operator
     */
    Flux<String> flatMapIterableExample(Flux<String> mountains) {
        return null;
    }

    public static Flux<String> asyncRemoteCall(String s) {
        return Flux.just(s.split("")).delayElements(Duration.ofMillis(10));
    }

    public static Iterable<String> syncRemoteCall(String s) throws InterruptedException {
        Thread.sleep(10);
        return Arrays.asList(s.split(""));

    }

    public static Flux<String> asyncRemoteCallWithErrorSignal(String s) {
        if ("Dexter".equals(s)) {
            return Flux.error(new RuntimeException("Not Breaking Bad character"));
        } else return asyncRemoteCall(s);
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    static class ColorCount {
        String color;
        Long count;
    }


}
