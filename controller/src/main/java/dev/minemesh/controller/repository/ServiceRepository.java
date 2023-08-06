package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ServiceRepository extends ReactiveCrudRepository<ServiceModel, String> {

    Mono<ServiceModel> updateStateById(String id, ServiceState state);

}
