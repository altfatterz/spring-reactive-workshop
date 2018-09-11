package ch.open;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Part08AdvancedOperatorsTests {

    Part08AdvancedOperators workshop = new Part08AdvancedOperators();

    @Test
    public void createFlux() {
        StepVerifier.create(workshop.createFlux())
                .expectNext(1)
                .expectNext(2)
                .expectComplete().verify();

    }

    @Test
    public void generateFlux() {
        StepVerifier.create(workshop.generateFlux(0)).verifyComplete();

        StepVerifier.create(workshop.generateFlux(2))
                .expectNext(0)
                .expectNext(2)
                .verifyComplete();

        StepVerifier.create(workshop.generateFlux(10))
                .expectNext(0, 2, 4, 6, 8, 10, 12, 14, 16, 18)
                .verifyComplete();
    }

    @Test
    public void removeNulls() {
        StepVerifier.create(workshop.removeNulls(Flux.just(-1, 30, 13, 9, 20)))
                .expectNext("M", "I", "T")
                .verifyComplete();

    }

    @Test
    public void buffer() {
        Flux<Integer> flux = Flux.range(1, 10);

        StepVerifier.create(workshop.transform(flux))
                .expectNext(Arrays.asList(1, 2, 3))
                .expectNext(Arrays.asList(4, 5, 6))
                .expectNext(Arrays.asList(7, 8, 9))
                .expectNext(Arrays.asList(10))
                .expectComplete()
                .verify();
    }




}
