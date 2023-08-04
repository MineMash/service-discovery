package dev.minemesh.servicediscovery.common;

import java.util.List;

public interface Metadata {

    List<MetadataEntry> findAll();

    String findByKey(String key);

    List<String> findMultipleByKeys(List<String> keys);

    interface MetadataEntry {

        String getKey();

        String getValue();

    }

}
