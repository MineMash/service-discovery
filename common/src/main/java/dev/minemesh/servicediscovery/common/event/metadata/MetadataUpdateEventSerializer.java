package dev.minemesh.servicediscovery.common.event.metadata;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;

import java.io.*;

public class MetadataUpdateEventSerializer implements KafkaEventSerializer {

    public static final int SERIALIZER_ID = 0x101;

    @Override
    public int getSerializerId() {
        return SERIALIZER_ID;
    }

    @Override
    public boolean accepts(KafkaEvent event) {
        return event instanceof MetadataUpdateEvent;
    }

    @Override
    public byte[] serialize(KafkaEvent kafkaEvent) throws IOException {
        MetadataUpdateEvent event = (MetadataUpdateEvent) kafkaEvent;

        try (ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);

            objectOut.writeUTF(event.getId());
            objectOut.writeUTF(event.getKey());
            objectOut.writeUTF(event.getNewValue());

            return bytesOut.toByteArray();
        }
    }

    @Override
    public MetadataUpdateEvent deserialize(byte[] bytes) throws IOException{
        try (ByteArrayInputStream bytesOut = new ByteArrayInputStream(bytes)) {
            ObjectInputStream objectOut = new ObjectInputStream(bytesOut);

            String id = objectOut.readUTF();
            String key = objectOut.readUTF();
            String newValue = objectOut.readUTF();

            return new MetadataUpdateEvent(id, key, newValue);
        }
    }
}
