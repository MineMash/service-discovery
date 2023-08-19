package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.model.RegisteredService;

public class ServiceUnregistrationEvent implements ServiceEvent {

    private final RegisteredService service;

    public ServiceUnregistrationEvent(RegisteredService service) {
        this.service = service;
    }

    @Override
    public RegisteredService getService() {
        return this.service;
    }
}
