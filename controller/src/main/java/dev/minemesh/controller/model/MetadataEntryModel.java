package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Metadata;

public class MetadataEntryModel implements Metadata.MetadataEntry {

    private String key;
    private String value;

    public MetadataEntryModel() {
    }

    public MetadataEntryModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
