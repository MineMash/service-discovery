package dev.minemesh.servicediscovery.common.event;

import java.io.IOException;
import java.util.Optional;

public interface KafkaEventSerializer<E extends KafkaEvent> {

    int getEventId();

    byte[] serialize(E event) throws IOException;

    Optional<E> deserialize(byte[] bytes);

}
