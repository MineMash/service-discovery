package dev.minemesh.servicediscovery.common;

import java.util.Collection;
import java.util.Map;

public interface Service {

    Network getNetwork();

    ServiceState getState();

    String findMetadata(String key);

    Map<String, String> findMultiMetadata(Collection<String> keys);

    Map<String, String> findAllMetadata();

}
