package dev.minemesh.servicediscovery.common.event;

import java.util.Optional;

public interface KafkaEventSerializer<E extends KafkaEvent> {

    int getEventId();

    boolean accept(KafkaEvent event);

    byte[] serialize(E event);

    Optional<E> deserialize(byte[] bytes);

}
