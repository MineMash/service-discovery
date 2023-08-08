package dev.minemesh.servicediscovery.common;

import java.util.Collection;
import java.util.Map;

public interface MetadataHolder {

    String getMetadata(String key);

    Map<String, String> getMultiMetadata(Collection<String> keys);

    Map<String, String> getAllMetadata();

}
