package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.model.RegisteredService;

import java.io.IOException;
import java.util.Optional;

public non-sealed class ServiceUnregistrationEventSerializer extends ServiceEventSerializer<ServiceUnregistrationEvent> {

    public ServiceUnregistrationEventSerializer(RegisteredServiceFactory serviceFactory) {
        super(serviceFactory);
    }

    @Override
    public int getEventId() {
        return ServiceUnregistrationEvent.EVENT_ID;
    }

    @Override
    public Optional<ServiceUnregistrationEvent> deserialize(byte[] bytes) {
        RegisteredService service = super.serviceFactory.newService();

        try {
            service.deserialize(bytes);
            return Optional.of(new ServiceUnregistrationEvent(service));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
