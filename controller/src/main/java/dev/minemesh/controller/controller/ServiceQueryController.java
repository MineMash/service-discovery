package dev.minemesh.controller.controller;

import dev.minemesh.controller.dao.ServiceDao;
import dev.minemesh.controller.model.Service;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceQueryController {

    private ServiceDao serviceDao;

    @QueryMapping
    public Optional<Service> getService(@Argument String id) {
        return this.serviceDao.getService(id);
    }

    @QueryMapping
    public List<Service> getAllServices() {
        return this.serviceDao.getAllServices();
    }

}
