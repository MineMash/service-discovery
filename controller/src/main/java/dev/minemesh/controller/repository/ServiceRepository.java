package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceRepository extends CrudRepository<ServiceModel, String> {

    Optional<ServiceModel> updateStateById(String id, ServiceState state);

}
