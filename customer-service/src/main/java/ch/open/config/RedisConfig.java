package ch.open.config;

import ch.open.customer.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Customer> reactiveJsonCustomerRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, Customer> serializationContext = RedisSerializationContext
                .<String, Customer>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(Customer.class))
                .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}
