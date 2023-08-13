package dev.minemesh.servicediscovery.common.event.service;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.model.RegisteredService;

public interface ServiceEvent extends KafkaEvent {

    RegisteredService getService();

}
