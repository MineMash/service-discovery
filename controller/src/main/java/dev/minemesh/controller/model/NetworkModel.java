package dev.minemesh.controller.model;

import dev.minemesh.servicediscovery.common.Network;

public class NetworkModel implements Network {

    private String address;
    private int runningPort;
    private int healthCheckPort;

    public NetworkModel() {
    }

    public NetworkModel(String address, int runningPort, int healthCheckPort) {
        this.address = address;
        this.runningPort = runningPort;
        this.healthCheckPort = healthCheckPort;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public int getRunningPort() {
        return this.runningPort;
    }

    @Override
    public int getHealthCheckPort() {
        return this.healthCheckPort;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRunningPort(int runningPort) {
        this.runningPort = runningPort;
    }

    public void setHealthCheckPort(int healthCheckPort) {
        this.healthCheckPort = healthCheckPort;
    }
}
