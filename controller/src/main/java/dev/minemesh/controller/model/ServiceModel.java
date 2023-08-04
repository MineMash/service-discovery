package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Metadata;
import dev.minemesh.servicediscovery.common.Network;
import dev.minemesh.servicediscovery.common.RegisteredService;
import dev.minemesh.servicediscovery.common.ServiceState;
import org.springframework.data.util.Lazy;

import java.lang.ref.Reference;
import java.util.UUID;

public class ServiceModel implements RegisteredService {

    private String id;
    private Network network;
    private ServiceState state;
    private UUID metadataReference;

    private final Lazy<MetadataModel> metadata = Lazy.of(() -> new MetadataModel(this.metadataReference));

    public ServiceModel() {}

    public ServiceModel(String id, Network network, ServiceState state, UUID metadataReference) {
        this.id = id;
        this.network = network;
        this.state = state;
        this.metadataReference = metadataReference;
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
        return this.metadata.get();
    }

    public UUID getMetadataReference() {
        return this.metadataReference;
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
        this.metadataReference = ((MetadataModel) metadata).getReferenceId();
    }

    public void setMetadataReference(UUID metadataReference) {
        this.metadataReference = metadataReference;
    }
}
