package dev.minemesh.controller.model;

public class Network {
    private String address;
    private int runningPort;
    private int healthCheckPort;

    public Network(String address, int runningPort, int healthCheckPort) {
        this.address = address;
        this.runningPort = runningPort;
        this.healthCheckPort = healthCheckPort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRunningPort() {
        return runningPort;
    }

    public void setRunningPort(int runningPort) {
        this.runningPort = runningPort;
    }

    public int getHealthCheckPort() {
        return healthCheckPort;
    }

    public void setHealthCheckPort(int healthCheckPort) {
        this.healthCheckPort = healthCheckPort;
    }
}
