package ch.open;

import ch.open.generator.Generator;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Part07DeferTest {

    Part07Defer workshop = new Part07Defer();

    @Test
    public void generate() {
        Generator generator = new Generator();

        Flux<Integer> flux = workshop.generate(generator);
        assertThat(generator.getNrOfCalls(), is(0));

        StepVerifier.create(flux).expectNextCount(5).verifyComplete();

        assertThat(generator.getNrOfCalls(), is(5));
    }
}
