package dev.minemesh.controller.service;

import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

     RegisteredService registerService(HeadlessServiceModel headless);

    boolean unregisterService(String id);

    Optional<RegisteredService> updateServiceState(String id, ServiceState state);

    Optional<RegisteredService> updateServiceMetadata(String id, MetadataEntry entry);

    Optional<ServiceModel> findById(String id);

    List<ServiceModel> findAll();

}
