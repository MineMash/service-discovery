package dev.minemesh.servicediscovery.common.event.metadata;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;

public class MetadataUpdateEvent implements KafkaEvent {

    private final String id;
    private final String key;
        private final String newValue;

    public MetadataUpdateEvent(String id, String key, String newValue) {
        this.id = id;
        this.key = key;
        this.newValue = newValue;
    }

    public String getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public String getNewValue() {
        return this.newValue;
    }
}
