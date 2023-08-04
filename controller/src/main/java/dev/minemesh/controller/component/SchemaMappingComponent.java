package dev.minemesh.controller.component;

import dev.minemesh.controller.model.MetadataModel;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchemaMappingComponent {

    @SchemaMapping
    public String get(MetadataModel metadata, @Argument String key) {
        return null;
    }

    @SchemaMapping
    public List<String> getMultiple(MetadataModel metadata, @Argument List<String> keys) {
        return null;
    }

    @SchemaMapping
    public List<MetadataModel> entries(MetadataModel metadata) {
        return null;
    }

}
