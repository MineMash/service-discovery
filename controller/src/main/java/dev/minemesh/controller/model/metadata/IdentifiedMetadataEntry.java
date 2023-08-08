package dev.minemesh.controller.model.metadata;

import java.util.Map;

public class IdentifiedMetadataEntry implements Map.Entry<MetadataIdentifier, String> {

    private MetadataIdentifier identifier;
    private String value;

    public IdentifiedMetadataEntry() {
    }

    public IdentifiedMetadataEntry(MetadataIdentifier identifier, String value) {
        this.identifier = identifier;
        this.value = value;
    }

    @Override
    public MetadataIdentifier getKey() {
        return this.identifier;
    }

    public void setIdentifier(MetadataIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String setValue(String value) {
        String old = this.value;
        this.value = value;
        return old;
    }
}
