package dev.minemesh.controller.model;

import java.util.List;

public class Service {

    private String id;
    private ServiceState state;
    private Network network;
    private Object metadata;

    public Service(String id, ServiceState state, Network network, Object metadata) {
        this.id = id;
        this.state = state;
        this.network = network;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceState getState() {
        return state;
    }

    public void setState(ServiceState state) {
        this.state = state;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public static class Headless {

        private ServiceState state;
        private Network network;
        private List<Object> metadata;

        public Headless(ServiceState state, Network network, List<Object> metadata) {
            this.state = state;
            this.network = network;
            this.metadata = metadata;
        }

        public ServiceState getState() {
            return state;
        }

        public void setState(ServiceState state) {
            this.state = state;
        }

        public Network getNetwork() {
            return network;
        }

        public void setNetwork(Network network) {
            this.network = network;
        }

        public List<Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(List<Object> metadata) {
            this.metadata = metadata;
        }
    }
}
