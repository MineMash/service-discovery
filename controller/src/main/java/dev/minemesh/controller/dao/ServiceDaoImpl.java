package dev.minemesh.controller.dao;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.Service;
import dev.minemesh.servicediscovery.common.ServiceState;

import java.util.List;
import java.util.Optional;

public class ServiceDaoImpl implements ServiceDao {

    @Override
    public Optional<RegisteredService> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<RegisteredService> findAll() {
        return List.of();
    }

    @Override
    public ServiceModel registerService(Service service) {
        return null;
    }

    @Override
    public boolean unregisterService(String id) {
        return false;
    }

    @Override
    public Optional<RegisteredService> updateServiceState(String id, ServiceState state) {
        return Optional.empty();
    }

    @Override
    public Optional<RegisteredService> updateServiceMetadata(String id, String key, String value) {
        return Optional.empty();
    }
}
