package dev.minemesh.controller.model;

public class MetadataIdentifier {

    private String serviceId;
    private String key;

    public MetadataIdentifier(String serviceId, String key) {
        this.serviceId = serviceId;
        this.key = key;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
