package dev.minemesh.controller.model;

import java.util.List;

public class Metadata {

    private List<MetadataEntry> entries;

    public List<MetadataEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MetadataEntry> entries) {
        this.entries = entries;
    }

    public static class MetadataEntry {

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
