package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.model.RegisteredService;

public class ServiceUnregistrationEvent implements ServiceEvent {

    public static final int EVENT_ID = 0x02;

    private final RegisteredService service;

    public ServiceUnregistrationEvent(RegisteredService service) {
        this.service = service;
    }

    @Override
    public int getEventId() {
        return EVENT_ID;
    }

    @Override
    public RegisteredService getService() {
        return this.service;
    }
}
