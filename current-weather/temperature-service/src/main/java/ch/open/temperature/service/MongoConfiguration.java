package ch.open.temperature.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


@Component
public class MongoConfiguration implements ApplicationRunner {

    private final MongoTemplate mongo;

    public MongoConfiguration(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void run(ApplicationArguments args) {

        mongo.dropCollection(Measurement.class);

        // tailable cursor needs a capped collection
        int sizeInBytes = 1024 * 1024;
        mongo.createCollection(Measurement.class, CollectionOptions.empty().size(sizeInBytes).capped()); }
}
