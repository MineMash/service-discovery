package dev.minemesh.servicediscovery.common;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;

import java.io.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class KafkaEventManager {

    private final List<KafkaEventSerializer> serializers;
    private final SerializerSelectionStrategy strategy;

    private KafkaEventManager(List<KafkaEventSerializer> serializers, SerializerSelectionStrategy strategy) {
        this.serializers = serializers;
        this.strategy = strategy;
    }

    public byte[] serialize(KafkaEvent event) throws IOException {
        try {
            List<KafkaEventSerializer> matchingSerializers = this.serializers.stream()
                    .filter(s -> s.accepts(event))
                    .toList();
            KafkaEventSerializer serializer = this.chooseSerializer(matchingSerializers);

            int serializerId = serializer.getSerializerId();
            byte[] serializedEvent = serializer.serialize(event);

            try (ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()) {
                ObjectOutputStream out = new ObjectOutputStream(bytesOut);
                out.writeInt(serializerId);
                out.write(serializedEvent);

                return bytesOut.toByteArray();
            }
        } catch (Exception e) {
            throw new IOException("Error during serialization of %s".formatted(event), e);
        }
    }

    public KafkaEvent deserialize(byte[] bytes) throws IOException {
        try(ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes)) {
            ObjectInputStream input = new ObjectInputStream(bytesIn);

            int serializerId = input.readInt();
            KafkaEventSerializer serializer = this.findSerializerById(serializerId);

            return serializer.deserialize(bytes);
        } catch (Exception e) {
            throw new IOException("Error during deserialization of KafkaEvent", e);
        }
    }

    private KafkaEventSerializer findSerializerById(int serializerId) {
        for (KafkaEventSerializer serializer : this.serializers) {
            if (serializer.getSerializerId() != serializerId) continue;

            return serializer;
        }

        throw new IllegalStateException("Cannot find serializer for id %d (0x%s)"
                .formatted(serializerId, Integer.toHexString(serializerId)));
    }

    private KafkaEventSerializer chooseSerializer(List<KafkaEventSerializer> matchingSerializers) throws IOException {
        if (matchingSerializers.isEmpty()) throw new IOException("No matching serializer found.");

        return switch (this.strategy) {
            case FIRST -> matchingSerializers.get(0);
            case LAST -> matchingSerializers.get(matchingSerializers.size() - 1);
            case RANDOM -> matchingSerializers.get(
                            ThreadLocalRandom.current().nextInt(matchingSerializers.size()));
            case THROW_EXCEPTION -> throw new IOException("To many matching serializers found.");
            default -> throw new AssertionError("Strategy %s is not implemented yet.".formatted(this.strategy));
        };
    }

    public enum SerializerSelectionStrategy {

        FIRST,
        LAST,
        RANDOM,
        THROW_EXCEPTION

    }

}
