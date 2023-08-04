package dev.minemesh.controller.controller;

import dev.minemesh.controller.dao.ServiceDao;
import dev.minemesh.controller.model.Service;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ServiceMutationController {

    private ServiceDao serviceDao;

    @MutationMapping
    public Service registerService(@Argument Service.Headless headless) {
        return this.serviceDao.registerService(headless);
    }

    @MutationMapping
    public boolean unregisterService(@Argument String id) {
        return this.serviceDao.unregisterService(id);
    }

    @MutationMapping
    public Optional<Service> updateServiceState(@Argument String id, @Argument ServiceState state) {
        return this.serviceDao.updateServiceState(id, state);
    }

    @MutationMapping
    public Optional<Service> updateServiceMetadata(@Argument String id, @Argument String key, @Argument String value) {
        return this.serviceDao.updateServiceMetadata(id, key, value);
    }

}
