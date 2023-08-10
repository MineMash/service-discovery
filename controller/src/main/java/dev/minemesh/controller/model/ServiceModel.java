package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.model.Network;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;

public class ServiceModel implements RegisteredService {

    private String id;
    private NetworkModel network;
    private ServiceState state;

    public ServiceModel() {}

    public ServiceModel(String id, NetworkModel network, ServiceState state) {
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
