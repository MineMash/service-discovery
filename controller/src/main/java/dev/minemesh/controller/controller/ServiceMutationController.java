package dev.minemesh.controller.controller;

import dev.minemesh.controller.dao.ServiceDao;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.Service;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ServiceMutationController {

    private final ServiceDao serviceDao;

    public ServiceMutationController(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @MutationMapping
    public ServiceModel registerService(@Argument Service service) {
        return this.serviceDao.registerService(service);
    }

    @MutationMapping
    public boolean unregisterService(@Argument String id) {
        return this.serviceDao.unregisterService(id);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceState(@Argument String id, @Argument ServiceState state) {
        return this.serviceDao.updateServiceState(id, state);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceMetadata(@Argument String id, @Argument String key, @Argument String value) {
        return this.serviceDao.updateServiceMetadata(id, key, value);
    }

}
