package ch.open.reactiveproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

import java.time.Duration;

@SpringBootApplication
@Slf4j
public class ReactiveProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveProducerApplication.class, args);
    }

    private static final String QUEUE = "reactive";

    @Override
    public void run(String... args) {

        Sender sender = ReactorRabbitMq.createSender();

        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));

        Flux<OutboundMessageResult> confirmations = sender.sendWithPublishConfirms(
                flux.map(i -> new OutboundMessage("", QUEUE, ("Message_" + i).getBytes())));

        sender.declareQueue(QueueSpecification.queue(QUEUE))
                .thenMany(confirmations)
                .doOnError(e -> log.error("Send failed", e))
                .subscribe(result -> {
                    if (result.isAck()) {
                        log.info("Message {} sent successfully", new String(result.getOutboundMessage().getBody()));
                    }
                });

    }
}
