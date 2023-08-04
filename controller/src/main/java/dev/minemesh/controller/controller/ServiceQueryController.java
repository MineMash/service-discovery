package dev.minemesh.controller.controller;

import dev.minemesh.controller.dao.ServiceDao;
import dev.minemesh.servicediscovery.common.RegisteredService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceQueryController {

    private ServiceDao serviceDao;

    @QueryMapping
    public Optional<RegisteredService> getService(@Argument String id) {
        return this.serviceDao.findById(id);
    }

    @QueryMapping
    public List<RegisteredService> getAllServices() {
        return this.serviceDao.findAll();
    }

}
