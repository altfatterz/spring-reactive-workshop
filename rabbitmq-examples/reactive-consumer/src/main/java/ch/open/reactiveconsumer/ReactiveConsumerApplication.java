package ch.open.reactiveconsumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.ReactorRabbitMq;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

@Slf4j
@SpringBootApplication
public class ReactiveConsumerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveConsumerApplication.class, args);
    }

    private static final String QUEUE = "reactive";

    @Override
    public void run(String... args) {

        Receiver receiver = ReactorRabbitMq.createReceiver();

        // creating the queue if the producer didn't create it yet, otherwise receiver cannot connect
        Sender sender = ReactorRabbitMq.createSender();
        Mono<AMQP.Queue.DeclareOk> queueDeclaration = sender.declareQueue(QueueSpecification.queue(QUEUE));

        Flux<Delivery> messages = receiver.consumeAutoAck(QUEUE);

        queueDeclaration.thenMany(messages)
                .subscribe(delivery -> log.info("Received message {}", new String(delivery.getBody())));
    }
}
