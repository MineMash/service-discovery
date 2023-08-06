package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Network;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;

import java.util.UUID;

public class ServiceModel implements RegisteredService {

    private String id;
    private NetworkModel network;
    private ServiceState state;

    public ServiceModel() {}

    public ServiceModel(String id, NetworkModel network, ServiceState state, UUID metadataReference) {
        this.id = id;
        this.network = network;
        this.state = state;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Network getNetwork() {
        return this.network;
    }

    @Override
    public ServiceState getState() {
        return this.state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNetwork(NetworkModel network) {
        this.network = network;
    }

    public void setState(ServiceState state) {
        this.state = state;
    }
}
