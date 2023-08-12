package dev.minemesh.controller.service;

import dev.minemesh.controller.configuration.KafkaConfiguration;
import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final MetadataRepository metadataRepository;
    private final Producer<String, RegisteredService> kafkaProducer;

    public ServiceServiceImpl(ServiceRepository serviceRepository, MetadataRepository metadataRepository, ProducerFactory<String, RegisteredService> producerFactory) {
        this.serviceRepository = serviceRepository;
        this.metadataRepository = metadataRepository;
        this.kafkaProducer = producerFactory.createProducer();
    }

    @Override
    public RegisteredService registerService(HeadlessServiceModel headless) {
        RegisteredService registeredService = this.serviceRepository.save(headless.toServiceModel());
        this.metadataRepository.saveAll(registeredService.getId(), headless.getMetadata());

        this.kafkaProducer.send(new ProducerRecord<>(KafkaConfiguration.REGISTRATION_TOPIC, registeredService));

        return registeredService;
    }

    @Override
    public boolean unregisterService(String id) {
        boolean changed = this.serviceRepository.deleteById(id);

        if (changed)
            this.metadataRepository.deleteAll(id);

        Optional<ServiceModel> service = this.findById(id);

        if (service.isEmpty()) return false;

        this.serviceRepository.deleteById(id);
        this.metadataRepository.deleteAll(id);

        this.kafkaProducer.send(new ProducerRecord<>(KafkaConfiguration.UNREGISTRATION_TOPIC, service.get()));

        return true;
    }

    @Override
    public Optional<RegisteredService> updateServiceState(String id, ServiceState state) {
        return this.serviceRepository.findById(id)
                .map(serviceModel -> {
                    serviceModel.setState(state);
                    ServiceModel saved = this.serviceRepository.save(serviceModel);

                    this.kafkaProducer.send(new ProducerRecord<>(KafkaConfiguration.STATE_UPDATE_TOPIC, saved));
                    return saved;
                });
    }

    @Override
    public Optional<RegisteredService> updateServiceMetadata(String id, MetadataEntry entry) {
        Optional<RegisteredService> serviceOptional = this.serviceRepository.findById(id).map(s -> s);

        serviceOptional.ifPresent(service -> {
            this.metadataRepository.save(id, entry);
            this.kafkaProducer.send(new ProducerRecord<>(KafkaConfiguration.METADATA_UPDATE_TOPIC, service));
        });

        return serviceOptional;
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
