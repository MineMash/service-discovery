package dev.minemesh.servicediscovery.common.model;

import dev.minemesh.servicediscovery.common.model.Serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface Network extends Serializable {

    String getAddress();

    int getRunningPort();

    int getHealthCheckPort();

    @Override
    default void serialize(ObjectOutputStream output) throws IOException {
        output.writeUTF(this.getAddress());
        output.writeInt(this.getRunningPort());
        output.writeInt(this.getHealthCheckPort());
    }
}
