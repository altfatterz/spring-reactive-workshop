package ch.open;

import ch.open.generator.Generator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Part07Defer {

    // TODO create a Flux using the provided Generator to generate 5 random integers. Look into Mono.just, Mono.defer and repeat operators
    public Flux<Integer> generate(Generator generator) {
        return Mono.defer(() -> Mono.just(generator.generate())).repeat(5); // TO BE REMOVED
    }


}
