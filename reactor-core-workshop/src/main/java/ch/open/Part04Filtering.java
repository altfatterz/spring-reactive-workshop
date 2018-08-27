package ch.open;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Filtering a Sequence
public class Part04Filtering {

    // TODO return only the even numbers emitted from the given publisher
    Flux<Integer> filterEven(Flux<Integer> flux) {
        return flux.filter(i -> i % 2 == 0); // TO BE REMOVED
    }

    // TODO ignore the duplicates form the given publisher
    Flux<Integer> ignoreDuplicates(Flux<Integer> flux) {
        return flux.distinct(); // TO BE REMOVED
    }

    // TODO emit the last element or 100 if the flux is empty
    Mono<Integer> emitLast(Flux<Integer> flux) {
        return flux.last(100); // TO BE REMOVED
    }

    // TODO ignore emitted items until a value greater than 10 is emitted
    Flux<Integer> ignoreUntil(Flux<Integer> flux) {
        return flux.skipUntil(integer -> integer > 10); // TO BE REMOVED
    }

    // TODO expect at most one or empty and signal error if more
    Mono<Integer> expectAtMostOneOrEmpty(Flux<Integer> flux) {
        return flux.singleOrEmpty(); // TO BE REMOVED
    }

}
