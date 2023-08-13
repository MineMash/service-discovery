package dev.minemesh.controller.service;

import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

     RegisteredService registerService(HeadlessServiceModel headless);

    boolean unregisterService(String id);

    Optional<ServiceState> updateServiceState(String id, ServiceState state);

    Optional<ServiceModel> findById(String id);

    List<ServiceModel> findAll();

}
