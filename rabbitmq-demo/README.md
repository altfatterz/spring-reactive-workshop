### RabbitMQ Examples

1. Start RabbitMQ using Docker

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

2. Verify that you can login to the management console:

http://hocalhost:15672
Credentials: guest/guest

3. Start the `reactive-consumer` application

Verify that the `reactive` queue has been created

5. Start the `reactive-producer` application

```bash
2018-09-10 08:21:19.149  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_0 sent successfully
2018-09-10 08:21:20.143  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_1 sent successfully
2018-09-10 08:21:21.144  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_2 sent successfully
2018-09-10 08:21:22.145  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_3 sent successfully
2018-09-10 08:21:23.144  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_4 sent successfully
2018-09-10 08:21:24.143  INFO 41434 --- [   rabbitmq-nio] c.o.r.ReactiveProducerApplication        : Message Message_5 sent successfully
```

6. Verify the `reactive-consumer` logs:

```bash
2018-09-10 08:21:19.154  INFO 41368 --- [pool-1-thread-4] c.o.r.ReactiveConsumerApplication        : Received message Message_0
2018-09-10 08:21:20.144  INFO 41368 --- [pool-1-thread-5] c.o.r.ReactiveConsumerApplication        : Received message Message_1
2018-09-10 08:21:21.144  INFO 41368 --- [pool-1-thread-6] c.o.r.ReactiveConsumerApplication        : Received message Message_2
2018-09-10 08:21:22.145  INFO 41368 --- [pool-1-thread-7] c.o.r.ReactiveConsumerApplication        : Received message Message_3
2018-09-10 08:21:23.145  INFO 41368 --- [pool-1-thread-8] c.o.r.ReactiveConsumerApplication        : Received message Message_4
```
