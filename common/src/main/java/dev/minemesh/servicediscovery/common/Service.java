package dev.minemesh.servicediscovery.common;

import java.util.Collection;
import java.util.Map;

public interface Service {

    Network getNetwork();

    ServiceState getState();

}
