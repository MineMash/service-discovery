package dev.minemesh.servicediscovery.common.model;

import java.io.*;

public interface Serializable {

    default byte[] serialize() throws IOException {
        try (ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bytesOut);
            this.serialize(out);

            return bytesOut.toByteArray();
        }
    }

    void serialize(ObjectOutputStream output) throws IOException;

    default void deserialize(byte[] bytes) throws IOException {
        try (ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes)) {
            ObjectInputStream in = new ObjectInputStream(bytesInput);

            this.deserialize(in);
        }
    }

    void deserialize(ObjectInputStream input) throws IOException;

}
