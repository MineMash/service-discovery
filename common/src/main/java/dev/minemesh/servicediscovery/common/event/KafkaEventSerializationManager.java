package dev.minemesh.servicediscovery.common.event;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static dev.minemesh.servicediscovery.common.util.Precheck.checkNotNull;

public final class KafkaEventSerializationManager {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private final List<KafkaEventSerializer> serializers;
    private final SerializerSelectionStrategy strategy;

    private KafkaEventSerializationManager(List<KafkaEventSerializer> serializers, SerializerSelectionStrategy strategy) {
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
                            RANDOM.nextInt(matchingSerializers.size()));
            case THROW_EXCEPTION -> {
                if (matchingSerializers.size() > 1)
                    throw new IOException("To many matching serializers found.");
                else
                    yield matchingSerializers.get(0);
            }
            default -> throw new AssertionError("Strategy %s is not implemented yet.".formatted(this.strategy));
        };
    }

    public enum SerializerSelectionStrategy {

        FIRST,
        LAST,
        RANDOM,
        THROW_EXCEPTION

    }

    public static class Builder {
        private LinkedList<KafkaEventSerializer> serializers = new LinkedList<>();
        private SerializerSelectionStrategy strategy = SerializerSelectionStrategy.FIRST;

        public Builder setSerializers(List<KafkaEventSerializer> serializers) {
            this.serializers = serializers == null
                    ? new LinkedList<>()
                    : new LinkedList<>(serializers);
            return this;
        }

        public Builder addSerializer(KafkaEventSerializer serializer) {
            this.serializers.add(checkNotNull("serializer", serializer));
            return this;
        }

        public Builder addFirstSerializer(KafkaEventSerializer serializer) {
            this.serializers.addFirst(checkNotNull("serializer", serializer));
            return this;
        }

        public Builder addLastSerializer(KafkaEventSerializer serializer) {
            this.serializers.addLast(checkNotNull("serializer", serializer));
            return this;
        }

        public Builder addAllSerializers(Collection<KafkaEventSerializer> serializers) {
            this.serializers.addAll(checkNotNull("serializers", serializers));
            return this;
        }

        public Builder addAllSerializers(KafkaEventSerializer... serializers) {
            this.serializers.addAll(Arrays.asList(checkNotNull("serializers", serializers)));
            return this;
        }

        public Builder setStrategy(SerializerSelectionStrategy strategy) {
            this.strategy = strategy == null
                    ? SerializerSelectionStrategy.FIRST
                    : strategy;
            return this;
        }

        public KafkaEventSerializationManager createKafkaEventManager() {
            return new KafkaEventSerializationManager(this.serializers, this.strategy);
        }
    }
}
