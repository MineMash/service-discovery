package dev.minemesh.controller.dao;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.Service;
import dev.minemesh.servicediscovery.common.ServiceState;

import java.util.List;
import java.util.Optional;

public interface ServiceDao {


    Optional<RegisteredService> findById(String id);

    List<RegisteredService> findAll();

    ServiceModel registerService(Service service);

    boolean unregisterService(String id);

    Optional<RegisteredService> updateServiceState(String id, ServiceState state);

    Optional<RegisteredService> updateServiceMetadata(String id, String key, String value);
}
