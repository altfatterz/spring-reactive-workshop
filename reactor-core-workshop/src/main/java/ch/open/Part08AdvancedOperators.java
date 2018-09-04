package ch.open;

import reactor.core.publisher.Flux;

public class Part08AdvancedOperators {

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
                });  // TO BE REMOVED
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
        }); // TO BE REMOVED
    }
}
