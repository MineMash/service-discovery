package dev.minemesh.controller.controller;

import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.RegisteredService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceQueryController {

    private ServiceRepository serviceRepository = null;

    @QueryMapping
    public Optional<RegisteredService> getService(@Argument String id) {
        return this.serviceRepository.findById(id);
    }

    @QueryMapping
    public List<RegisteredService> getAllServices() {
        List<RegisteredService> list = new LinkedList<>();
        this.serviceRepository.findAll().forEach(list::add);
        return list;
    }

}
