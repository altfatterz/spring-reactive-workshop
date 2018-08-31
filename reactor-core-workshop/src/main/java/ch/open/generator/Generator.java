package ch.open.generator;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Random;

public class Generator {

    private Random random = new Random();
    private int nrOfCalls;

    public Integer generate() {
        nrOfCalls++;
        return random.nextInt(10);
    }

    public Iterable<Integer> generateAllBlocking() {
        nrOfCalls++;
        return Arrays.asList(1, 2, 3, 4, 5);
    }

    public Flux<Integer> generateAllReactive() {
        nrOfCalls++;
        return Flux.just(1, 2, 3, 4, 5);
    }

    public int getNrOfCalls() {
        return nrOfCalls;
    }
}
