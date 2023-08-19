package dev.minemesh.controller.service;

import dev.minemesh.controller.configuration.KafkaConfiguration;
import dev.minemesh.controller.model.HeadlessServiceModel;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.controller.repository.ServiceRepository;
import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.event.service.ServiceRegistrationEvent;
import dev.minemesh.servicediscovery.common.event.service.ServiceUnregistrationEvent;
import dev.minemesh.servicediscovery.common.event.state.StateUpdateEvent;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import dev.minemesh.servicediscovery.common.model.ServiceState;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final MetadataRepository metadataRepository;
    private final Producer<String, KafkaEvent> kafkaProducer;

    public ServiceServiceImpl(ServiceRepository serviceRepository, MetadataRepository metadataRepository, ProducerFactory<String, KafkaEvent> producerFactory) {
        this.serviceRepository = serviceRepository;
        this.metadataRepository = metadataRepository;
        this.kafkaProducer = producerFactory.createProducer();
    }

    @Override
    public RegisteredService registerService(HeadlessServiceModel headless) {
        RegisteredService registeredService = this.serviceRepository.save(headless.toServiceModel());
        this.metadataRepository.saveAll(registeredService.getId(), headless.getMetadata());

        this.kafkaProducer.send(new ProducerRecord<>(
                KafkaConfiguration.REGISTRATION_TOPIC,
                new ServiceRegistrationEvent(registeredService)
        ));

        return registeredService;
    }

    @Override
    public boolean unregisterService(String id) {
        Optional<ServiceModel> service = this.findById(id);

        if (service.isEmpty()) return false;

        this.serviceRepository.deleteById(id);
        this.metadataRepository.deleteAll(id);

        this.kafkaProducer.send(new ProducerRecord<>(
                KafkaConfiguration.UNREGISTRATION_TOPIC,
                new ServiceUnregistrationEvent(service.get())
        ));

        return true;
    }

    @Override
    public Optional<ServiceState> updateServiceState(String id, ServiceState state) {
        return this.serviceRepository.findById(id)
                .map(serviceModel -> {
                    ServiceState old = serviceModel.getState();
                    serviceModel.setState(state);
                    ServiceModel saved = this.serviceRepository.save(serviceModel);

                    this.kafkaProducer.send(new ProducerRecord<>(
                            KafkaConfiguration.STATE_UPDATE_TOPIC,
                            new StateUpdateEvent(id, old, state)
                    ));
                    return old;
                });
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
