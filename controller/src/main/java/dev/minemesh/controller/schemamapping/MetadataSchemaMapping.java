package dev.minemesh.controller.schemamapping;

import dev.minemesh.controller.model.MetadataModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.Metadata;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MetadataSchemaMapping {

    @SchemaMapping
    public MetadataModel metadata(ServiceModel service) {
        return (MetadataModel) service.getMetadata();
    }

    @SchemaMapping
    public Optional<String> findByKey(MetadataModel model, @Argument String key) {
        return Optional.empty();
    }

    @SchemaMapping
    public List<Metadata.MetadataEntry> findAll(MetadataModel model) {
        return List.of();
    }

    @SchemaMapping
    public List<String> findMultipleByKeys(MetadataModel model, @Argument List<String> keys) {
        return List.of();
    }

}
