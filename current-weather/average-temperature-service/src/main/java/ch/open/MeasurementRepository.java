package ch.open;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class MeasurementRepository {

    private final String MEASUREMENTS_REDIS_HASH_NAME = "measurements";

    private final ReactiveRedisOperations<String, Measurement> template;

    public MeasurementRepository(ReactiveRedisOperations<String, Measurement> template) {
        this.template = template;
    }

    public Flux<Measurement> findAll() {
        return template.<String, Measurement>opsForHash().values(MEASUREMENTS_REDIS_HASH_NAME);
    }

    public Mono<Measurement> save(Measurement measurement) {
        if (measurement.getId() == null) {
            measurement.setId(UUID.randomUUID().toString());
        }
        return template.<String, Measurement>opsForHash()
                .put(MEASUREMENTS_REDIS_HASH_NAME, measurement.getId(), measurement)
                .log()
                .map(m -> measurement);

    }

    public Mono<Boolean> deleteAll() {
        return template.<String, Measurement>opsForHash().delete(MEASUREMENTS_REDIS_HASH_NAME);
    }


}

