package dev.minemesh.controller.dao;

import dev.minemesh.controller.model.Service;
import dev.minemesh.controller.model.ServiceState;

import java.util.List;
import java.util.Optional;

public interface ServiceDao {


    Optional<Service> getService(String id);

    List<Service> getAllServices();

    Service registerService(Service.Headless headless);

    boolean unregisterService(String id);

    Optional<Service> updateServiceState(String id, ServiceState state);

    Optional<Service> updateServiceMetadata(String id, String key, String value);
}
