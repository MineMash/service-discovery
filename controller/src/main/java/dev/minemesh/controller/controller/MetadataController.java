package dev.minemesh.controller.controller;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.service.MetadataService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @SchemaMapping(typeName = "Service")
    public Optional<String> findMetadata(ServiceModel service, @Argument String key) {
        return this.metadataService.findById(new MetadataIdentifier(service.getId(), key));
    }

    @SchemaMapping(typeName = "Service")
    public List<MetadataEntry> findMultiMetadata(ServiceModel service, @Argument List<String> keys) {
        return this.metadataService.findAllById(service.getId(), keys);
    }

    @SchemaMapping(typeName = "Service")
    public List<MetadataEntry> findAllMetadata(ServiceModel service) {
        return this.metadataService.findAll(service.getId());
    }

    @MutationMapping
    public boolean updateServiceMetadata(@Argument String id, @Argument MetadataEntry entry) {
        return this.metadataService.update(id, entry);
    }

}
