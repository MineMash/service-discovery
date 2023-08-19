package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.model.RegisteredService;

public class ServiceRegistrationEvent implements ServiceEvent {

    private final RegisteredService service;

    public ServiceRegistrationEvent(RegisteredService service) {
        this.service = service;
    }

    @Override
    public RegisteredService getService() {
        return this.service;
    }
}
