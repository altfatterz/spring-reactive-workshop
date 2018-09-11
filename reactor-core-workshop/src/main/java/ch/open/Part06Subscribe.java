package ch.open;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Slf4j
public class Part06Subscribe {

    /**
     * TODO 1
     *
     * <p>
     * Return a Flux which emits: 1, 2, 3 integers and then completes.
     * <p>
     * Subscribe to this Flux without any Consumer. Inspect the logs by using the 'log' operator.
     */
    public Flux<Integer> subscribeEmpty() {
        return null;
    }

    /**
     * TODO 2
     *
     * <p>
     * Return a Flux which emits: 1, 2, 3 integers and then completes
     * <p>
     * Subscribe to this Flux using a Consumer, which prints out the elements. Inspect the logs by using the 'log' operator.
     */
    public Flux<Integer> subscribeWithConsumer() {
        return null;
    }

    /**
     * TODO 3
     *
     * <p>
     * Return a Flux which emits: 1, 2, 3 integers and then completes
     * <p>
     * Subscribe to this Flux using a Consumer (which prints out the elements) and complete Consumer
     * (which prints out "completed"). Inspect the logs by using the 'log' operator.
     */
    public Flux<Integer> subscribeWithConsumerAndCompleteConsumer() {
        return null;
    }


    /**
     * TODO 4
     * <p>
     * Return a Flux which emits: 1, 2, 3 integers and then signals error.
     * <p>
     * Subscribe to this Flux using a Consumer (which prints out the elements) and error Consumer
     * (which logs the error message).
     * <p>
     * Inspect the logs by using the 'log' operator.
     */
    public Flux<Integer> subscribeWithConsumerAndErrorConsumer() {
        return null;
    }

    /**
     * TODO 5
     * <p>
     * Return a Flux which emits: 1, 2, 3 integers and then completes.
     * <p>
     * Subscribe to this Flux using a Subscription Consumer requesting only 2 elements.
     * <p>
     * Inspect the logs by using the 'log' operator.
     */
    public Flux<Integer> subscribeWithSubscriptionConsumer() {
        return null;
    }

    /**
     * TODO 6
     * <p>
     * Return a Flux which emits 1, 2, 3 and then completes.
     * <p>
     * Subscribe to this Flux using a Subscriber requesting in onSubscribe 1 element and then later as much as
     * the current element it was. Inspect the logs using the 'log' operator.
     */
    public Flux<Integer> subscribeWithSubscription() {
        return null;
    }

}