package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Metadata;

import java.util.List;
import java.util.UUID;

public class MetadataModel implements Metadata {

    private UUID referenceId;

    public MetadataModel() {
    }

    public MetadataModel(UUID referenceId) {
        this.referenceId = referenceId;
    }

    public UUID getReferenceId() {
        return this.referenceId;
    }

    @Override
    public List<MetadataEntry> findAll() {
        throw new UnsupportedOperationException("Unsupported on controller. Use MetadataSchemaMapping.findMultipleByKeys(List<String>) instead.");
    }

    @Override
    public String findByKey(String key) {
        throw new UnsupportedOperationException("Unsupported on controller. Use MetadataSchemaMapping.findMultipleByKeys(List<String>) instead.");
    }

    @Override
    public List<String> findMultipleByKeys(List<String> keys) {
        throw new UnsupportedOperationException("Unsupported on controller. Use MetadataSchemaMapping.findMultipleByKeys(List<String>) instead.");
    }

    public void setReferenceId(UUID referenceId) {
        this.referenceId = referenceId;
    }
}
