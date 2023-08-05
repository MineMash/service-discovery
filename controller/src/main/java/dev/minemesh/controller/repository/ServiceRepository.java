package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.util.StringUtil;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.Service;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<RegisteredService, String> {

    default RegisteredService registerService(Service service) {
        return this.save(fromService(service));
    }

    default boolean unregisterService(String id) {
        return false;
    }

    Optional<RegisteredService> updateServiceState(String id, ServiceState state);

    Optional<RegisteredService> updateServiceMetadata(String id, String key, String value);


    ///////////////////////
    // Utility methods
    ///////////////////////

    private static RegisteredService fromService(Service service) {
        if (service instanceof RegisteredService registeredService) return registeredService;

        return new ServiceModel(
                generateId(),
                service.getNetwork(),
                service.getState(),
                null
        );
    }

    private static String generateId() {
        // TODO redis call for time and counter

        return StringUtil.generateIdString(0L, 0, (short) 0);
    }

}
