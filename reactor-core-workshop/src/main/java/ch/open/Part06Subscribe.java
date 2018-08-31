package ch.open;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

public class Part06Subscribe {

    // TODO return a Flux which emits: 1, 2, 3 integers and then completes
    // TODO subscribe to this Flux without any Consumer, error Consumer and complete Consumer. Inspect the logs by using the 'log' operator.
    public Flux<Integer> subscribeEmpty() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.log().subscribe();
        return flux;
    }

    // TODO return a Flux which emits: 1, 2, 3 integers and then completes
    // TODO subscribe to this Flux using a Consumer, which prints out the elements. Inspect the logs by using the 'log' operator.
    public Flux<Integer> subscribeWithConsumer() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.log().subscribe(integer -> System.out.println(integer));
        return flux;
    }

    // TODO return a Flux which emits:1, 2, 3 integers and then completes
    // TODO subscribe to this Flux using a Consumer (which prints out the elements) and complete Consumer (which prints out "completed"). Inspect the logs by using the 'log' operator.
    public Flux<Integer> subscribeWithConsumerAndCompleteConsumer() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.subscribe(integer -> System.out.println(integer), null, () -> System.out.println("completed"));
        return flux;
    }

    // TODO return a Flux which emits:1, 2, 3 integers and then signals error
    // TODO subscribe to this Flux using a Consumer (which prints out the elements) and error Consumer (which logs the error message). Inspect the logs by using the 'log' operator.
    public Flux<Integer> subscribeWithConsumerAndErrorConsumer() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4).map(i -> {
            if (i != 4) {
                return i;
            } else {
                throw new IllegalStateException("boom");
            }
        });
        flux.log().subscribe(integer -> System.out.println(integer), throwable -> System.out.println(throwable.getMessage()));
        return flux;
    }

    // TODO return a Flux which emits: 1, 2, 3 integers and then completes
    // TODO subscribe to this Flux using a Subscription Consumer requesting only 2 elements. Inspect the logs by using the 'log' operator.
    public Flux<Integer> subscribeWithSubscriptionConsumer() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.log().subscribe(null, null, null, new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) {
                subscription.request(2);
            }
        });
        return flux;
    }

    // TODO return a Flux which emits 1, 2, 3 and then completes
    // TODO subscribe to this Flux using a Subscriber requesting in onSubscribe 1 element and then later as much as the current element it was. Inspect the logs using the 'log' operator.
    public Flux<Integer> subscribeWithSubscription() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.log().subscribe(new Subscriber<Integer>() {

            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                s.request(integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        return flux;
    }

}