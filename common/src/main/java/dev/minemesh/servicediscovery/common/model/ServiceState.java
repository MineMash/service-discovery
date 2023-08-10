package dev.minemesh.servicediscovery.common.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public enum ServiceState implements Serializable {

    CREATED,
    READY,
    HEALTHY,
    UNHEALTHY,
    STOPPING,
    STOPPED,
    UNKNOWN;


    @Override
    public void serialize(ObjectOutputStream output) throws IOException {
        output.writeInt(this.ordinal());
    }

    @Override
    public void deserialize(ObjectInputStream input) throws IOException {
        throw new UnsupportedOperationException("Use ServiceState.deserializeState(ObjectInputStream)");
    }

    public static Optional<ServiceState> deserializeState(byte[] bytes) throws IOException {
        try (ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes)) {
            return deserializeState(new ObjectInputStream(bytesInput));
        }
    }

    public static Optional<ServiceState> deserializeState(ObjectInputStream input) throws IOException {
        ServiceState[] values = values();
        int index = input.readInt();

        if (index >= values.length || index < 0)
            return Optional.empty();

        return Optional.of(values[index]);
    }

}