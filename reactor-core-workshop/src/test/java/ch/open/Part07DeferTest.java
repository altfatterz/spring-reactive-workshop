package ch.open;

import ch.open.generator.Generator;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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

    @Test
    public void monoToInteger() {
        Mono<Integer> mono = Mono.just(5);
        Integer integer = workshop.monoToInteger(mono);
        assertThat(integer, is(5));
    }

    @Test
    public void fluxToIntegers() {
        Flux<Integer> flux = Flux.range(1, 5);
        Iterable<Integer> integers = workshop.fluxToIntegers(flux);
        Iterator<Integer> iterator = integers.iterator();
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.next(), is(4));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void slowPublisherFastSubscriber() {
        Generator generator = new Generator();

        Flux<Integer> flux = workshop.slowPublisherFactSubsciber(generator);
        assertThat(generator.getNrOfCalls(), is(0));

        StepVerifier.create(flux).expectNextCount(5).verifyComplete();
    }

    @Test
    public void fastPublisherSlowSubscriber() {
        Generator generator = new Generator();


    }


}
