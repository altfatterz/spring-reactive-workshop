package ch.open.asyncproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static ch.open.asyncproducer.AsyncProducerApplication.QUEUE;

@Component
@Slf4j
public class Producer {

    private AsyncRabbitTemplate asyncRabbitTemplate;

    public Producer(AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    private long count;

    @Scheduled(fixedDelay = 1000L)
    public void send() {
        count++;
        String message = "Message_" + count;

        AsyncRabbitTemplate.RabbitConverterFuture<String> future =
                asyncRabbitTemplate.convertSendAndReceive("", QUEUE, message);

        log.info("Thread: '{}' sent message '{}'", Thread.currentThread().getName(), message);

        future.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(String result) {
                log.info("Thread: '{}' result: '{}'", Thread.currentThread().getName(), result);
            }
        });
    }

}