package dev.minemesh.controller.model.metadata;

import java.util.Map;

public class MetadataEntry implements Map.Entry<String, String> {

    private String key;
    private String value;

    public MetadataEntry() {
    }

    public MetadataEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public String setValue(String value) {
        String old = this.value;
        this.value = value;
        return old;
    }
}
