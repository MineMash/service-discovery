package dev.minemesh.controller.controller;

import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.RegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceQueryController {

    private final ServiceRepository serviceRepository;

    public ServiceQueryController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @QueryMapping
    public Mono<RegisteredService> findServiceById(@Argument String id) {
        return this.serviceRepository.findById(id).cast(RegisteredService.class);
    }

    @QueryMapping
    public Flux<RegisteredService> findAllServices() {
        return this.serviceRepository.findAll().cast(RegisteredService.class);
    }

}
