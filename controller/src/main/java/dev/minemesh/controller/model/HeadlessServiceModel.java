package dev.minemesh.controller.model;

import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.servicediscovery.common.model.Network;
import dev.minemesh.servicediscovery.common.model.Service;
import dev.minemesh.servicediscovery.common.model.ServiceState;

import java.util.List;

public class HeadlessServiceModel implements Service {

    private NetworkModel network;
    private ServiceState state;

    private List<MetadataEntry> metadata;

    public HeadlessServiceModel() {}

    public HeadlessServiceModel(NetworkModel network, ServiceState state, List<MetadataEntry> metadata) {
        this.network = network;
        this.state = state;
        this.metadata = metadata;
    }

    @Override
    public Network getNetwork() {
        return this.network;
    }

    @Override
    public ServiceState getState() {
        return this.state;
    }

    public void setNetwork(NetworkModel network) {
        this.network = network;
    }

    public void setState(ServiceState state) {
        this.state = state;
    }

    public List<MetadataEntry> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(List<MetadataEntry> metadata) {
        this.metadata = metadata;
    }

    public ServiceModel toServiceModel() {
        return new ServiceModel(
                null,
                this.network,
                this.state
        );
    }

}
