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

        // Capped collections are fixed-size collections that support high-throughput operations
        // that insert and retrieve documents based on insertion order.

        // Capped collections work in a way similar to circular buffers: once a collection fills its allocated space,
        // it makes room for new documents by overwriting the oldest documents in the collection.

        // Capped collections guarantee preservation of the insertion order.
        // As a result, queries do not need an index to return documents in insertion order.
        // Without this indexing overhead, capped collections can support higher insertion throughput.

        mongo.createCollection(Measurement.class, CollectionOptions.empty().size(sizeInBytes).capped()); }
}
