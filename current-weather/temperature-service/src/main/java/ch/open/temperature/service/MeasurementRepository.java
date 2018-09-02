package ch.open.temperature.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MeasurementRepository extends ReactiveMongoRepository<Measurement, String> {

    // Infinite Streams with Tailable Cursor

    // - By default, MongoDB automatically closes a cursor when the client exhausts all results supplied by the cursor
    // - Closing a cursor on exhaustion turns a stream into a finite stream.
    // - For capped collections, you can use a Tailable Cursor that remains open after the client consumes all initially returned data.
    // - Using tailable cursors with a reactive data types allows construction of infinite streams.
    // - A tailable cursor remains open until it is closed externally.
    // - It emits data as new documents arrive in a capped collection.
    @Tailable
    Flux<Measurement> findAllBy();
}
