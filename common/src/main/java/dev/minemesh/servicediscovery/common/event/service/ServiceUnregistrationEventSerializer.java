package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.model.RegisteredService;

import java.io.IOException;
import java.util.Optional;

public non-sealed class ServiceUnregistrationEventSerializer extends ServiceEventSerializer {

    public static final int SERIALIZER_ID = 0x002;

    public ServiceUnregistrationEventSerializer(RegisteredServiceFactory serviceFactory) {
        super(serviceFactory);
    }

    @Override
    public int getSerializerId() {
        return SERIALIZER_ID;
    }

    @Override
    public byte[] serialize(KafkaEvent event) throws IOException {
        RegisteredService service = ((ServiceUnregistrationEvent) event).getService();

        return service.serialize();
    }

    @Override
    public boolean accepts(KafkaEvent event) {
        return event instanceof ServiceUnregistrationEvent;
    }

    @Override
    public ServiceUnregistrationEvent deserialize(byte[] bytes) throws IOException {
        RegisteredService service = super.serviceFactory.newService();
        service.deserialize(bytes);

        return new ServiceUnregistrationEvent(service);
    }

}
