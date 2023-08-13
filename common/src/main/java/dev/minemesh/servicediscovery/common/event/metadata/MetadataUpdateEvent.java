package dev.minemesh.servicediscovery.common.event.metadata;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;

public class MetadataUpdateEvent implements KafkaEvent {

    public static final int EVENT_ID = 0x101;

    private final String id;
    private final String key;

    private final String oldValue;
    private final String newValue;

    public MetadataUpdateEvent(String id, String key, String oldValue, String newValue) {
        this.id = id;
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public String getNewValue() {
        return this.newValue;
    }

    @Override
    public int getEventId() {
        return EVENT_ID;
    }
}
