package dev.minemesh.servicediscovery.common;

import java.util.List;

public interface Metadata {

    List<MetadataEntry> getEntries();

    String get(String key);

    List<String> getMultiple(List<String> keys);

    interface MetadataEntry {

        String getKey();

        String getValue();

    }

}
