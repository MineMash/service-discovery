package dev.minemesh.controller.serliazier;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class ServiceModelSerializer implements Serializer<RegisteredService> {

    @Override
    public byte[] serialize(String topic, RegisteredService data) {
        if (data == null) return null;

        try {
            return data.serialize();
        } catch (IOException e) {
            return null;
        }
    }

}
