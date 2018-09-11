package ch.open;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Learn about filtering the sequence.
 */
public class Part04Filtering {

    /**
     * TODO 1
     * <p>
     * Return only the even numbers emitted by the given publisher
     */
    Flux<Integer> filterEven(Flux<Integer> flux) {
        return null;
    }

    /**
     * TODO 2
     * <p>
     * Ignore the duplicates emitted by the given publisher
     */
    Flux<Integer> ignoreDuplicates(Flux<Integer> flux) {
        return null;
    }

    /**
     * TODO 3
     * <p>
     * Emit the last element or 100 if the flux is empty.
     */
    Mono<Integer> emitLast(Flux<Integer> flux) {
        return null;
    }

    /**
     * TODO 4
     * <p>
     * Ignore items until a value greater than 10 is emitted.
     */
    Flux<Integer> ignoreUntil(Flux<Integer> flux) {
        return null;
    }

    /**
     * TODO 5
     * <p>
     * Expect at most one or empty and signal error if more.
     */
    Mono<Integer> expectAtMostOneOrEmpty(Flux<Integer> flux) {
        return null;
    }


    /**
     * TODO 6
     *
     * Filter the provided Publisher where the criteria is asynchronously computed via {@link #asyncFilter} method
     */
    Flux<Integer> asyncComputedFilter(Flux<Integer> flux) {
        return null;
    }



    Mono<Boolean> asyncFilter(Integer integer) {
        return Mono.just(integer % 2 == 0).delayElement(Duration.ofMillis(500));
    }



}
