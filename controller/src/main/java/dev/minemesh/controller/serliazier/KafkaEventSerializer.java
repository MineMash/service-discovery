package dev.minemesh.controller.serliazier;

import dev.minemesh.servicediscovery.common.event.KafkaEventSerializationManager;
import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class KafkaEventSerializer implements Serializer<KafkaEvent> {

    private final KafkaEventSerializationManager manager;

    public KafkaEventSerializer(KafkaEventSerializationManager manager) {
        this.manager = manager;
    }

    @Override
    public byte[] serialize(String topic, KafkaEvent data) {
        try {
            return manager.serialize(data);
        } catch (IOException e) {
            throw new AssertionError("Cannot serialize KafkaEvent", e);
        }
    }
}
