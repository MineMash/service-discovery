package dev.minemesh.servicediscovery.common.event.metadata;

import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;

import java.io.*;
import java.util.Optional;

public class MetadataUpdateEventSerializer implements KafkaEventSerializer<MetadataUpdateEvent> {

    @Override
    public int getEventId() {
        return MetadataUpdateEvent.EVENT_ID;
    }

    @Override
    public byte[] serialize(MetadataUpdateEvent event) throws IOException {
        try (ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);

            objectOut.writeUTF(event.getId());
            objectOut.writeUTF(event.getKey());
            objectOut.writeUTF(event.getOldValue());
            objectOut.writeUTF(event.getNewValue());

            return bytesOut.toByteArray();
        }
    }

    @Override
    public Optional<MetadataUpdateEvent> deserialize(byte[] bytes) {
        try (ByteArrayInputStream bytesOut = new ByteArrayInputStream(bytes)) {
            ObjectInputStream objectOut = new ObjectInputStream(bytesOut);

            String id = objectOut.readUTF();
            String key = objectOut.readUTF();
            String oldValue = objectOut.readUTF();
            String newValue = objectOut.readUTF();

            return Optional.of(new MetadataUpdateEvent(id, key, oldValue, newValue));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
