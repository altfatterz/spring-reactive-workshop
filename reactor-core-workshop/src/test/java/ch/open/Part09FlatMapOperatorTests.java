package ch.open;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static ch.open.Part09FlatMapOperator.*;

public class Part09FlatMapOperatorTests {

    Part09FlatMapOperator workshop = new Part09FlatMapOperator();

    @Test
    public void simpleFlatMap() {
        Flux<String> greetings = Flux.just("hallo", "hoi", "ciao", "szia");


        Flux<String> result = workshop.simpleFlatMap(greetings);

        StepVerifier.create(result)
                .expectNextCount(16)
                .expectComplete()
                .verify();
    }

    @Test
    public void flatMapSequential() {
        Flux<String> friends = Flux.just("Joe", "Chandler", "Monica", "Phoebe", "Rachel", "Ross");

        Flux<String> result = workshop.flatMapSequential(friends);

        StepVerifier.create(result)
                .expectNext("J", "O", "E")
                .expectNext("C", "H", "A", "N", "D", "L", "E", "R")
                .expectNext("M", "O", "N", "I", "C", "A")
                .expectNext("P", "H", "O", "E", "B", "E")
                .expectNext("R", "A", "C", "H", "E", "L")
                .expectNext("R", "O", "S", "S")
                .verifyComplete();
    }

    @Test
    public void flatMapWithConcurrency() {
        Flux<String> capitals = Flux.just("Bern", "Berlin", "Budapest");

        Flux<String> result = workshop.flatMapWithConcurrency(capitals);

        StepVerifier.create(result)
                .expectNext("B", "e", "r", "n")
                .expectNext("B", "e", "r", "l", "i", "n")
                .expectNext("B", "u", "d", "a", "p", "e", "s", "t")
                .verifyComplete();
    }

    @Test
    public void concatMapExample() {
        Flux<String> rivers = Flux.just("Nile", "Danube", "Ganges");

        Flux<String> result = workshop.concatMapExample(rivers);

        StepVerifier.create(result)
                .expectNext("N", "i", "l", "e")
                .expectNext("D", "a", "n", "u", "b", "e")
                .expectNext("G", "a", "n", "g", "e", "s")
                .verifyComplete();

    }

    @Test
    public void fluxOfFluxes() {
        Flux<String> colors = Flux.just("red", "green", "blue", "red", "yellow", "green", "green");

        Flux<ColorCount> result = workshop.fluxOfFluxes(colors);

        StepVerifier.create(result)
                .expectNext(new ColorCount("red", 2L))
                .expectNext(new ColorCount("green", 3L))
                .expectNext(new ColorCount("blue", 1L))
                .expectNext(new ColorCount("yellow", 1L))
                .verifyComplete();
    }

    @Test
    public void complexFlatMap() {
        Flux<String> values = Flux.range(1, 10).map(String::valueOf);

        Flux<String> result = workshop.complexFlatMap(values);

        StepVerifier.create(result)
                .expectNext("1", "2")
                .expectNext("---")
                .expectNext("3", "4")
                .expectNext("---")
                .expectNext("5", "6")
                .expectNext("---")
                .expectNext("7", "8")
                .expectNext("---")
                .expectNext("9", "10")
                .expectNext("---")
                .verifyComplete();

    }

    @Test
    public void flatMapOverrideError() {
        Flux<String> names = Flux.just("Walter", "Jesse", "Gus", "Saul", "Skyler", "Dexter", "Mike", "Hank");

        Flux<String> result = workshop.flatMapOverrideError(names);

        StepVerifier.create(result)
                .expectNextCount(32)
                .verifyComplete();

    }

    @Test
    public void flatMapManyExample() {
        Mono<String> greeting = Mono.just("hello");

        Flux<String> result = workshop.flatMapManyExample(greeting);

        StepVerifier.create(result)
                .expectNext("h", "e", "l", "l", "o")
                .verifyComplete();
    }

    @Test
    public void flatMapIterableExample() {
        Flux<String> mountains = Flux.just("Pilatus", "Rigi", "Titlis", "Matterhorn");

        Flux<String> result = workshop.flatMapIterableExample(mountains);

        StepVerifier.create(result)
                .expectNext("P", "i", "l", "a", "t", "u", "s")
                .expectNext("R", "i", "g", "i")
                .expectNext("T", "i", "t", "l", "i", "s")
                .expectNext("M", "a", "t", "t", "e", "r", "h", "o", "r", "n")
                .verifyComplete();
    }

}
