package dev.minemesh.servicediscovery.common.event.state;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.model.ServiceState;

public class StateUpdateEvent implements KafkaEvent {

    private final String serviceId;
    private final ServiceState oldState;
    private final ServiceState newState;

    public StateUpdateEvent(String serviceId, ServiceState oldState, ServiceState newState) {
        this.serviceId = serviceId;
        this.oldState = oldState;
        this.newState = newState;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public ServiceState getOldState() {
        return this.oldState;
    }

    public ServiceState getNewState() {
        return this.newState;
    }
}
