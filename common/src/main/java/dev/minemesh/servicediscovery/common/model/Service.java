package dev.minemesh.servicediscovery.common.model;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface Service extends Serializable {

    Network getNetwork();

    ServiceState getState();

    @Override
    default void serialize(ObjectOutputStream output) throws IOException {
        this.getNetwork().serialize(output);
        this.getState().serialize(output);
    }
}
