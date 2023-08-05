package dev.minemesh.controller.repository;

import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;

import java.util.Optional;

public class ServiceRepositoryImpl implements ServiceRepository {

    @Override
    public Optional<RegisteredService> updateServiceState(String id, ServiceState state) {
        return Optional.empty();
    }

    @Override
    public Optional<RegisteredService> updateServiceMetadata(String id, String key, String value) {
        return Optional.empty();
    }

    @Override
    public <S extends RegisteredService> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RegisteredService> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<RegisteredService> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<RegisteredService> findAll() {
        return null;
    }

    @Override
    public Iterable<RegisteredService> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(RegisteredService entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends RegisteredService> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
