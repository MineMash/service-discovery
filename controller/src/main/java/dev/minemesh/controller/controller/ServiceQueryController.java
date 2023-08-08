package dev.minemesh.controller.controller;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.RegisteredService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ServiceQueryController {

    private final ServiceRepository serviceRepository;
    private final MetadataRepository metadataRepository;

    public ServiceQueryController(ServiceRepository serviceRepository, MetadataRepository metadataRepository) {
        this.serviceRepository = serviceRepository;
        this.metadataRepository = metadataRepository;
    }

    @QueryMapping
    public Mono<RegisteredService> findServiceById(@Argument String id) {
        return this.serviceRepository.findById(id).cast(RegisteredService.class);
    }

    @QueryMapping
    public Flux<RegisteredService> findAllServices() {
        return this.serviceRepository.findAll().cast(RegisteredService.class);
    }

    @SchemaMapping
    public Mono<String> findMetadata(ServiceModel service, @Argument String key) {
        return this.metadataRepository.findByKey(new MetadataIdentifier(service.getId(), key));
    }

    @SchemaMapping
    public Flux<MetadataEntry> findMultiMetadata(ServiceModel service, @Argument List<String> keys) {
        return this.metadataRepository.findAllByKey(service.getId(), keys);
    }

    @SchemaMapping
    public Mono<List<MetadataEntry>> findAllMetadata(ServiceModel service) {
        return this.metadataRepository.findAll(service.getId());
    }

}
