package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;
import dev.minemesh.servicediscovery.common.model.RegisteredService;

public sealed abstract class ServiceEventSerializer
        implements KafkaEventSerializer
        permits ServiceRegistrationEventSerializer, ServiceUnregistrationEventSerializer {

    protected final RegisteredServiceFactory serviceFactory;

    protected ServiceEventSerializer(RegisteredServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @FunctionalInterface
    public interface RegisteredServiceFactory {

        RegisteredService newService();

    }
}
