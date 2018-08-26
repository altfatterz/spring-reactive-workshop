package ch.open;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class Part02Transform {

    // TODO create a flux which emits the length of the string values emitted by the provided flux.
    Flux<Integer> map(Flux<String> flux) {
        Flux<Tuple2<Long, String>> index = flux.index();
        return flux.map(s -> s.length());
    }

    // TODO create a Tuple2<Long, String> sequence recording the order in which source values were received using 1-based incrementing long
    Flux<Tuple2<Long, String>> index(Flux<String> flux) {
        return flux.index((i, v) -> Tuples.of(i + 1, v));
    }

    // TODO create a sequence which transforms the provided string values into uppercase and to the characters
    Flux<String> flatMap(Flux<String> flux) {
        return flux
                .map(s -> s.toUpperCase())
                .flatMap(s -> Flux.fromArray(s.split("")));
    }

    // TODO create a sequence which transforms the provided string values into uppercase and to the characters keeping the original sequence order
    Flux<String> flatMapSequential(Flux<String> flux) {
        return flux
                .map(s -> s.toUpperCase())
                .flatMapSequential(s -> Flux.fromArray(s.split("")));
    }





}
