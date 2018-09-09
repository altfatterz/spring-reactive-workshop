package ch.open.asyncconsumer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AsyncConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncConsumerApplication.class, args);
    }

    @Bean
    Queue requestQueue() {
        return QueueBuilder.durable("async").build();
    }

}
