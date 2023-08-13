package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;
import dev.minemesh.servicediscovery.common.model.RegisteredService;

import java.io.IOException;

public sealed abstract class ServiceEventSerializer<E extends ServiceEvent>
        implements KafkaEventSerializer<E>
        permits ServiceRegistrationEventSerializer, ServiceUnregistrationEventSerializer {

    protected final RegisteredServiceFactory serviceFactory;

    protected ServiceEventSerializer(RegisteredServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public byte[] serialize(E event) throws IOException {
        RegisteredService service = event.getService();

        return service.serialize();
    }

    @FunctionalInterface
    public interface RegisteredServiceFactory {

        RegisteredService newService();

    }
}
