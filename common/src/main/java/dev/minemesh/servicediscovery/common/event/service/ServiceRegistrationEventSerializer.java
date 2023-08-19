package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.model.RegisteredService;

import java.io.IOException;

public non-sealed class ServiceRegistrationEventSerializer extends ServiceEventSerializer {

    public static final int SERIALIZER_ID = 0x001;

    public ServiceRegistrationEventSerializer(RegisteredServiceFactory serviceFactory) {
        super(serviceFactory);
    }

    @Override
    public int getSerializerId() {
        return SERIALIZER_ID;
    }

    @Override
    public byte[] serialize(KafkaEvent event) throws IOException {
        RegisteredService service = ((ServiceRegistrationEvent) event).getService();

        return service.serialize();
    }

    @Override
    public boolean accepts(KafkaEvent event) {
        return event instanceof ServiceRegistrationEvent;
    }

    @Override
    public ServiceRegistrationEvent deserialize(byte[] bytes) throws IOException {
        RegisteredService service = super.serviceFactory.newService();

        service.deserialize(bytes);
        return new ServiceRegistrationEvent(service);
    }

}
