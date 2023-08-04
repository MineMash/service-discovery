package dev.minemesh.servicediscovery.common;

public interface Network {

    String getAddress();

    int getRunningPort();

    int getHealthCheckPort();

}
