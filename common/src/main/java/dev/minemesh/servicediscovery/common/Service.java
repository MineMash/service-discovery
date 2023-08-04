package dev.minemesh.servicediscovery.common;

public interface Service {

    Network getNetwork();

    ServiceState getState();

    Metadata getMetadata();

}
