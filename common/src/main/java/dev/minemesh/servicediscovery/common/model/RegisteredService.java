package dev.minemesh.servicediscovery.common.model;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface RegisteredService extends Service {

    String getId();

    @Override
    default void serialize(ObjectOutputStream output) throws IOException {
        output.writeUTF(this.getId());
        Service.super.serialize(output);
    }
}
