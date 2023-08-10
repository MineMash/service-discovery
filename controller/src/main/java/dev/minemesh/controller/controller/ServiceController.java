package dev.minemesh.controller.controller;

import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.service.ServiceService;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @QueryMapping
    public Optional<ServiceModel> findServiceById(@Argument String id) {
        return this.serviceService.findById(id);
    }

    @QueryMapping
    public List<ServiceModel> findAllServices() {
        return this.serviceService.findAll();
    }

    @MutationMapping
    public RegisteredService registerService(@Argument HeadlessServiceModel headless) {
        return this.serviceService.registerService(headless);
    }

    @MutationMapping
    public boolean unregisterService(@Argument String id) {
        return this.serviceService.unregisterService(id);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceState(@Argument String id, @Argument ServiceState state) {
        return this.serviceService.updateServiceState(id, state);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceMetadata(@Argument String id, @Argument MetadataEntry entry) {
        return this.serviceService.updateServiceMetadata(id, entry);
    }

}
