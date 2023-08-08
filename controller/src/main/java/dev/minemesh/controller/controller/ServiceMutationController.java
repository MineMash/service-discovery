package dev.minemesh.controller.controller;

import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Controller
public class ServiceMutationController {

    private final ServiceRepository serviceRepository;
    private final MetadataRepository metadataRepository;

    public ServiceMutationController(ServiceRepository serviceRepository, MetadataRepository metadataRepository) {
        this.serviceRepository = serviceRepository;
        this.metadataRepository = metadataRepository;
    }

    @MutationMapping
    public Mono<RegisteredService> registerService(@Argument HeadlessServiceModel headless) {
        return this.serviceRepository.save(headless.toServiceModel())
                .cast(RegisteredService.class)
                .flatMap(registeredService ->
                        // save metadata when id is ready
                        this.metadataRepository.saveAll(
                                registeredService.getId(),
                                headless.getMetadata()
                        // but we need to return the service
                        ).then(Mono.just(registeredService)));
    }

    @MutationMapping
    public Mono<Boolean> unregisterService(@Argument String id) {
        return this.serviceRepository.deleteById(id).then(Mono.just(Boolean.TRUE));
    }

    @MutationMapping
    public Mono<RegisteredService> updateServiceState(@Argument String id, @Argument ServiceState state) {
        return this.serviceRepository.updateStateById(id, state).cast(RegisteredService.class);
    }

    @MutationMapping
    public Optional<RegisteredService> updateServiceMetadata(@Argument String id, @Argument String key, @Argument String value) {
        return Optional.empty(); //this.serviceRepository.updateServiceMetadata(id, key, value); TODO metadata repository
    }

}
