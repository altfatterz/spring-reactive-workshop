package ch.open;

import ch.open.generator.Generator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Part07Defer {

    // TODO return a Flux using the provided Generator to generate 5 random integers. Look into Mono.just, Mono.defer and repeat operators
    public Flux<Integer> generate(Generator generator) {
        return Mono.defer(() -> Mono.just(generator.generate())).repeat(5); // TO BE REMOVED
    }

    // TODO return the integer emitted by the the provided Mono
    public Integer monoToInteger(Mono<Integer> mono) {
        return mono.block();
    }

    // TODO return the integers emitted by the provided Flux
    public Iterable<Integer> fluxToIntegers(Flux<Integer> flux) {
        return flux.toIterable();
    }

    // TODO create a Flux reading all integers from the blocking Generator#generateAll() deferred until the flux is subscribed
    // TODO and configure that each subscription will happen on a dedicated single-threaded worker from Schedulers.elastic
    public Flux<Integer> slowPublisherFactSubsciber(Generator generator) {
        return Flux.defer(() -> Flux.fromIterable(generator.generateAllBlocking()))
                .subscribeOn(Schedulers.elastic());
    }

    // TODO
    public Mono<Void> fastPublisherSlowSubscriber() {
        return null;
    }
}
