package dev.minemesh.controller.controller;

import dev.minemesh.controller.repository.ServiceRepository;
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

    private final ServiceRepository serviceRepository;

    public ServiceMutationController() {
        this.serviceRepository = null;
    }

    @MutationMapping
    public RegisteredService registerService(@Argument Service service) {
        return this.serviceRepository.registerService(service);
    }

    @MutationMapping
    public boolean unregisterService(@Argument String id) {
        return this.serviceRepository.unregisterService(id);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceState(@Argument String id, @Argument ServiceState state) {
        return this.serviceRepository.updateServiceState(id, state);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceMetadata(@Argument String id, @Argument String key, @Argument String value) {
        return this.serviceRepository.updateServiceMetadata(id, key, value);
    }

}
