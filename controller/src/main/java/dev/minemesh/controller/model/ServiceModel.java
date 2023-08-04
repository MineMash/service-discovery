package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Metadata;
import dev.minemesh.servicediscovery.common.Network;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;

public class ServiceModel implements RegisteredService {

    private String id;
    private Network network;
    private ServiceState state;
    private Metadata metadata;

    public ServiceModel() {}

    public ServiceModel(String id, Network network, ServiceState state, Metadata metadata) {
        this.id = id;
        this.network = network;
        this.state = state;
        this.metadata = metadata;
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

    @Override
    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setState(ServiceState state) {
        this.state = state;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
