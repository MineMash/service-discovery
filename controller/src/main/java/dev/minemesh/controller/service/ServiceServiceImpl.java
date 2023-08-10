package dev.minemesh.controller.service;

import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final MetadataRepository metadataRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository, MetadataRepository metadataRepository) {
        this.serviceRepository = serviceRepository;
        this.metadataRepository = metadataRepository;
    }

    @Override
    public RegisteredService registerService(HeadlessServiceModel headless) {
        RegisteredService registeredService = this.serviceRepository.save(headless.toServiceModel());
        this.metadataRepository.saveAll(registeredService.getId(), headless.getMetadata());

        return registeredService;
    }

    @Override
    public boolean unregisterService(String id) {
        boolean changed = this.serviceRepository.deleteById(id);

        if (changed)
            this.metadataRepository.deleteAll(id);

        return changed;
    }

    @Override
    public Optional<RegisteredService> updateServiceState(String id, ServiceState state) {
        return this.serviceRepository.findById(id)
                .map(serviceModel -> {
                    serviceModel.setState(state);
                    return this.serviceRepository.save(serviceModel);
                });
    }

    @Override
    public Optional<RegisteredService> updateServiceMetadata(String id, MetadataEntry entry) {
        Optional<RegisteredService> service = this.serviceRepository.findById(id).map(s -> s);

        if (service.isPresent()) this.metadataRepository.save(id, entry);

        return service;
    }

    @Override
    public Optional<ServiceModel> findById(String id) {
        return this.serviceRepository.findById(id);
    }

    @Override
    public List<ServiceModel> findAll() {
        return this.serviceRepository.findAll();
    }
}
