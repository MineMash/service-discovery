package dev.minemesh.controller.service;

import dev.minemesh.controller.configuration.KafkaConfiguration;
import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.repository.MetadataRepository;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetadataServiceImpl implements MetadataService {

    private final MetadataRepository metadataRepository;
    private final Producer<String, RegisteredService> producer;

    public MetadataServiceImpl(MetadataRepository metadataRepository, ProducerFactory<String, RegisteredService> producerFactory) {
        this.metadataRepository = metadataRepository;
        this.producer = producerFactory.createProducer();
    }

    @Override
    public Optional<String> findById(MetadataIdentifier metadataIdentifier) {
        return this.metadataRepository.findById(metadataIdentifier);
    }

    @Override
    public List<MetadataEntry> findAllById(String id, List<String> keys) {
        return this.metadataRepository.findAllById(id, keys);
    }

    @Override
    public List<MetadataEntry> findAll(String id) {
        return this.metadataRepository.findAll(id);
    }

    @Override
    public boolean update(String id, MetadataEntry entry) {
        boolean exists = this.metadataRepository.exists(id);

        if (!exists) return false;

        this.metadataRepository.save(id, entry);
        // TODO records rework
        this.producer.send(new ProducerRecord<>(KafkaConfiguration.METADATA_UPDATE_TOPIC, new ServiceModel()));

        return true;
    }
}
