package ch.open;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * Learn about the flatMap operator.
 *
 * map:       takes a Function<T, U> and returns a Flux<U>
 * flatMap:   takes a Function<T, Publisher<V>> and returns a Flux<V>
 *
 * You can pass a Function<T, Publisher<V>> to a map, but it wouldn't know what to do with the Publishers
 * and that would result in a Flux<Publisher<V>>, a sequence of inner publishers.
 *
 * flatMap expects a Publisher<V> for each T.
 * It knows what to do with it: subscribe to it and propagate its elements in the output sequence.
 *
 * As a result, the return type is Flux<V>: flatMap will flatten each inner Publisher<V> into the output sequence of all the Vs.
 *
 * https://raw.githubusercontent.com/reactor/projectreactor.io/master/src/main/static/assets/img/marble/flatmap.png
 *
 */
@Slf4j
public class Part09FlatMapOperator {

    // TODO
    Flux<String> simpleFlatMap(Flux<String> greetings) {
        return greetings.flatMap(greeting -> asyncRemoteCall(greeting)); // TO BE REMOVED
    }

    // TODO
    Flux<String> flatMapSequential(Flux<String> friends) {
        return friends
                .map(String::toUpperCase)
                .flatMapSequential(friend -> asyncRemoteCall(friend)); // TO BE REMOVED
    }

    // TODO
    Flux<String> flatMapWithConcurrency(Flux<String> capitals) {
        return capitals.flatMap(capital -> asyncRemoteCall(capital), 1); // TO BE REMOVED
    }

    // TODO
    Flux<String> concatMapExample(Flux<String> rivers) {
        return rivers.concatMap(river -> asyncRemoteCall(river)); // TO BE REMOVED
    }

    // TODO
    Flux<ColorCount> fluxOfFluxes(Flux<String> colors) {
        return colors.groupBy(color -> color)
                .flatMap(group -> group.count()
                        .map(count -> new ColorCount(group.key(), count))); // TO BE REMOVED
    }

    // TODO
    Flux<String> complexFlatMap(Flux<String> values) {
        return values.window(2)
                .flatMap(window -> window.flatMap(
                        Flux::just,
                        Flux::error,
                        () -> Flux.just("---")
                ));  // TO BE REMOVED
    }

    // TODO
    Flux<String> flatMapOverrideError(Flux<String> names) {
        return names.flatMap(name -> asyncRemoteCallWithErrorSignal(name)
                .flatMap(Flux::just,
                        err -> {
                            log.info("got exception but we ignore it");
                            return Flux.empty();
                        },
                        Flux::empty)
        );

    }

    // TODO
    Flux<String> flatMapManyExample(Mono<String> greeting) {
        return greeting.flatMapMany(s -> asyncRemoteCall(s));
    }

    // TODO
    Flux<String> flatMapIterableExample(Flux<String> mountains) {
        return mountains.flatMapIterable(s -> {
            try {
                return syncRemoteCall(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        });
    }

    public static Flux<String> asyncRemoteCall(String s) {
        return Flux.just(s.split("")).delayElements(Duration.ofMillis(10));
    }

    public static Iterable<String> syncRemoteCall(String s) throws InterruptedException {
        Thread.sleep(10);
        return Arrays.asList(s.split(""));

    }

    public static Flux<String> asyncRemoteCallWithErrorSignal(String s) {
        if ("Dexter".equals(s)) {
            return Flux.error(new RuntimeException("BOOM"));
        } else return asyncRemoteCall(s);
    }

    @Getter
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    static class ColorCount {
        String color;
        Long count;
    }


}
