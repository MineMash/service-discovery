package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Metadata;

import java.util.List;
import java.util.UUID;

public class MetadataModel implements Metadata {

    private UUID referenceId;

    private List<MetadataEntry> entries;

    public MetadataModel() {
    }

    public MetadataModel(UUID referenceId) {
        this.referenceId = referenceId;
    }

    public MetadataModel(UUID referenceId, List<MetadataEntry> entries) {
        this.referenceId = referenceId;
        this.entries = entries;
    }

    public UUID getReferenceId() {
        return this.referenceId;
    }

    @Override
    public List<MetadataEntry> getEntries() {
        return this.entries;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public List<String> getMultiple(List<String> keys) {
        return null;
    }

    public void setReferenceId(UUID referenceId) {
        this.referenceId = referenceId;
    }

    public void setEntries(List<MetadataEntry> entries) {
        this.entries = entries;
    }
}
