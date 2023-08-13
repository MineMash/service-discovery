package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.model.RegisteredService;

import java.io.IOException;
import java.util.Optional;

public non-sealed class ServiceRegistrationEventSerializer extends ServiceEventSerializer<ServiceRegistrationEvent> {

    public ServiceRegistrationEventSerializer(RegisteredServiceFactory serviceFactory) {
        super(serviceFactory);
    }

    @Override
    public int getEventId() {
        return ServiceRegistrationEvent.EVENT_ID;
    }

    @Override
    public Optional<ServiceRegistrationEvent> deserialize(byte[] bytes) {
        RegisteredService service = super.serviceFactory.newService();

        try {
            service.deserialize(bytes);
            return Optional.of(new ServiceRegistrationEvent(service));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
