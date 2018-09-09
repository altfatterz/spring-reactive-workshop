package ch.open.asyncconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = "async")
    public void process(@Payload String request) {
        log.info("Received '{}'", request);
    }

}
